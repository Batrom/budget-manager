package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.DebtDTO;
import com.batrom.budgetcalculator.model.Debt;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.repository.DebtRepository;
import com.batrom.budgetcalculator.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToFunction;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class DebtService {

    public void save(final List<Debt> debts) {
        debtRepository.saveAll(debts);
    }

    @Transactional
    public List<Debt> calculateDebts(final List<Product> products) {
        return wrapToFunction(this::getDebts).andThen(this::reverseAndConcat)
                                             .andThen(this::mergeDebts)
                                             .apply(products);
    }

    @Transactional
    public List<DebtDTO> getDebtsByMember(final String memberName) {
        return wrapToFunction(memberService::findByName)
                .andThen(member -> wrapToFunction(productService::findProductsForDebtsView)
                        .andThen(this::calculateDebts)
                        .andThen(debts -> filterDebtsForMember(member, debts))
                        .andThen(debts -> Stream.of(debts, debtRepository.findDebtsByDebtor(member))
                                                .flatMap(Collection::stream)
                                                .filter(this::isNotZero)
                                                .map(this::entityToDTO)
                                                .collect(toList()))
                        .apply(member))
                .apply(memberName);
    }

    private List<Debt> filterDebtsForMember(final Member member, final List<Debt> debts) {
        return debts.stream()
                    .filter(debt -> member.equals(debt.getDebtor()))
                    .collect(toList());
    }

    private DebtDTO entityToDTO(final Debt debt) {
        final DebtDTO debtDTO = new DebtDTO();
        debtDTO.setMe(debt.getDebtor().getName());
        debtDTO.setOtherMember(debt.getCreditor().getName());
        debtDTO.setCreationDate(formatDate(debt.getDate()));
        debtDTO.setDebt(debt.getAmount());
        return debtDTO;
    }

    private boolean isNotZero(final Debt debt) {
        return debt.getAmount().compareTo(BigDecimal.ZERO) != 0;
    }

    private String formatDate(final LocalDate date) {
        return DateUtils.mapToString(date, "MM.yyyy");
    }

    private List<Debt> getDebts(final List<Product> products) {
        return products.stream()
                       .map(this::productToDebts)
                       .flatMap(Collection::stream)
                       .collect(toList());
    }

    private List<Debt> productToDebts(final Product product) {
        return calculateSingleProductDebts(product, calculateAmount(product));
    }

    private BigDecimal calculateAmount(final Product product) {
        return product.getPrice()
                      .divide(BigDecimal.valueOf(product.getDebtorGroup()
                                                        .getMembers()
                                                        .size()), 8, RoundingMode.HALF_UP);
    }

    private List<Debt> calculateSingleProductDebts(final Product product, final BigDecimal amount) {
        return product.getDebtorGroup()
                      .getMembers()
                      .stream()
                      .filter(debtor -> !debtor.equals(product.getCreditor()))
                      .map(debtor -> {
                          final Debt debt = new Debt();
                          debt.setCreditor(product.getCreditor());
                          debt.setDebtor(debtor);
                          debt.setAmount(amount);
                          debt.setDate(DateUtils.getFirstDayOfMonth(product.getCreationDate()));
                          return debt;
                      })
                      .collect(toList());
    }

    private List<Debt> reverseAndConcat(final List<Debt> debts) {
        return Stream.of(debts, debts.stream()
                                     .map(this::reverse)
                                     .collect(toList()))
                     .flatMap(Collection::stream)
                     .collect(toList());
    }

    private List<Debt> mergeDebts(List<Debt> debts) {
        return debts.stream()
                    .collect(toMap(this::compositeKey, Debt::getAmount, BigDecimal::add))
                    .entrySet()
                    .stream()
                    .map(this::entryToDebt)
                    .collect(toList());
    }

    private List<Object> compositeKey(final Debt debt) {
        return Arrays.asList(debt.getCreditor(), debt.getDebtor(), debt.getDate());
    }

    private Debt entryToDebt(final Map.Entry<List<Object>, BigDecimal> entry) {
        final Debt debt = new Debt();
        debt.setCreditor((Member) entry.getKey().get(0));
        debt.setDebtor((Member) entry.getKey().get(1));
        debt.setDate((LocalDate) entry.getKey().get(2));
        debt.setAmount(entry.getValue());
        return debt;
    }

    private Debt reverse(final Debt debt) {
        final Debt reversedDebt = new Debt();
        reversedDebt.setCreditor(debt.getDebtor());
        reversedDebt.setAmount(debt.getAmount().negate());
        reversedDebt.setDebtor(debt.getCreditor());
        reversedDebt.setDate(debt.getDate());
        return reversedDebt;
    }

    private final DebtRepository debtRepository;

    private final ProductService productService;

    private final MemberService memberService;

    public DebtService(final DebtRepository debtRepository, final ProductService productService, final MemberService memberService) {
        this.debtRepository = debtRepository;
        this.productService = productService;
        this.memberService = memberService;
    }
}
