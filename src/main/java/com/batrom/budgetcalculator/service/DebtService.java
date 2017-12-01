package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.Debt;
import com.batrom.budgetcalculator.model.DebtDTO;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.model.User;
import com.batrom.budgetcalculator.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;

    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    public List<Debt> fillDebts(final Product product) {
        final List<User> debtors = product.getDebtorsGroup().getUsers();
        final BigDecimal amount = product.getPrice().divide(BigDecimal.valueOf(debtors.size()), 2, RoundingMode.FLOOR);
        return debtors.stream()
                      .filter(debtor -> !debtor.equals(product.getCreditor()))
                      .map(debtor -> mapToDebt(product, amount, debtor))
                      .collect(toList());
    }

    private Debt mapToDebt(Product product, BigDecimal amount, User debtor) {
        final Debt debt = new Debt();
        debt.setCreditor(product.getCreditor());
        debt.setDebtor(debtor);
        debt.setProduct(product);
        debt.setAmount(amount);
        debt.setCreationDate(product.getCreationDate());
        return debt;
    }

    public List<DebtDTO> getDebtsByUser(final String user) {
        final List<Debt> allDebts = getAllDebts();

        final List<DebtDTO> debtorAmount = mapToListOfDebtDTO(allDebts.stream()
                                                             .filter(debt -> debt.getDebtor().getName().equals(user))
                                                             .map(this::mapToDebtDTOForUserAsDebtor)
                                                             .collect(groupingBy(Function.identity(), reducing(BigDecimal.ZERO, DebtDTO::getDebt, BigDecimal::add))));

        final List<DebtDTO> creditorAmount = mapToListOfDebtDTO(allDebts.stream()
                                                                        .filter(debt -> debt.getCreditor().getName().equals(user))
                                                                        .map(this::mapToDebtDTOForUserAsCreditor)
                                                                        .collect(groupingBy(Function.identity(), reducing(BigDecimal.ZERO, DebtDTO::getDebt, BigDecimal::add))));

        final List<DebtDTO> overallDebt = mapToListOfDebtDTO(Stream.of(debtorAmount, creditorAmount)
                                                                   .flatMap(Collection::stream)
                                                                   .collect(groupingBy(Function.identity(), reducing(BigDecimal.ZERO, DebtDTO::getDebt, BigDecimal::add))));


        return overallDebt;
    }

    private DebtDTO mapToDebtDTOForUserAsDebtor(final Debt debt) {
        final DebtDTO debtDTO = new DebtDTO();
        debtDTO.setMe(debt.getDebtor().getName());
        debtDTO.setOtherUser(debt.getCreditor().getName());
        debtDTO.setDebt(debt.getAmount());
        debtDTO.setCreationDate(getFormattedDate(debt.getCreationDate()));
        return debtDTO;
    }

    private DebtDTO mapToDebtDTOForUserAsCreditor(final Debt debt) {
        final DebtDTO debtDTO = new DebtDTO();
        debtDTO.setMe(debt.getCreditor().getName());
        debtDTO.setOtherUser(debt.getDebtor().getName());
        debtDTO.setDebt(debt.getAmount().negate());
        debtDTO.setCreationDate(getFormattedDate(debt.getCreationDate()));
        return debtDTO;
    }

    private String getFormattedDate(final LocalDate date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy");
        return date.format(formatter);
    }

    private List<DebtDTO> mapToListOfDebtDTO(final Map<DebtDTO, BigDecimal> map) {
        return map.entrySet()
                  .stream()
                  .map(this::mapToDebtDTO)
                  .filter(debt -> debt.getDebt().compareTo(BigDecimal.ZERO) != 0)
                  .collect(toList());
    }

    private DebtDTO mapToDebtDTO(final Map.Entry<DebtDTO, BigDecimal> entry) {
        final DebtDTO debtDTO = new DebtDTO();
        debtDTO.setMe(entry.getKey().getMe());
        debtDTO.setOtherUser(entry.getKey().getOtherUser());
        debtDTO.setCreationDate(entry.getKey().getCreationDate());
        debtDTO.setDebt(entry.getValue());
        return debtDTO;
    }
}
