package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.ProductDTO;
import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.repository.ProductRepository;
import com.batrom.budgetcalculator.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToFunction;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class ProductService {

    public List<ProductDTO> findProductsForView(final String memberName) {
        final Member member = memberService.findByName(memberName);
        final List<MemberGroup> memberGroups = memberGroupService.findMemberGroupsByMember(member);
        return productRepository.findProductsForView(memberGroups, member, DateUtils.getSixtyDaysAgo(), Category.NONE)
                                .stream()
                                .map(this::entityToDTO)
                                .collect(Collectors.toList());
    }

    public ProductDTO save(final ProductDTO dto) {
        return wrapToFunction(this::dtoToEntity).andThen(productRepository::save)
                                                .andThen(this::entityToDTO)
                                                .apply(dto);
    }

    public ProductDTO update(final ProductDTO dto) {
        return save(dto);
    }

    @Transactional
    public List<ProductDTO> updateCategory(final Map<String, List<ProductDTO>> productsByCategoryMap) {
        productsByCategoryMap.entrySet()
                             .stream()
                             .filter(entry -> !CollectionUtils.isEmpty(entry.getValue()))
                             .forEach(entry -> productRepository.updateCategory(Category.getByPolishName(entry.getKey()), entry.getValue()
                                                                                                                               .stream()
                                                                                                                               .map(ProductDTO::getId)
                                                                                                                               .collect(toList())));
        return productsByCategoryMap.entrySet()
                                    .stream()
                                    .map(entry -> entry.getValue()
                                                       .stream()
                                                       .peek(productDTO -> productDTO.setCategory(entry.getKey()))
                                                       .collect(toList()))
                                    .flatMap(List::stream)
                                    .collect(toList());
    }

    public Long delete(final ProductDTO dto) {
        productRepository.deleteById(dto.getId());
        return dto.getId();
    }

    public List<Product> findProductsBetweenDates(final LocalDate from, final LocalDate to) {
        return productRepository.findProductsByCreationDateGreaterThanEqualAndCreationDateLessThan(from, to);
    }

    public List<Product> findProductsByDebtors(final Set<Member> debtors) {
        final Set<MemberGroup> memberGroups = debtors.stream()
                                                     .flatMap(member -> member.getMemberGroups().stream())
                                                     .collect(toSet());
        return productRepository.findProductsByDebtorGroupIn(memberGroups);
    }

    public List<Product> findProductsForDebtsView(final Member member) {
        final List<MemberGroup> memberGroups = memberGroupService.findMemberGroupsByMember(member);
        return productRepository.findProductsByDebtorGroupInOrCreditorAndCreationDateGreaterThanEqual(memberGroups, member, DateUtils
                .getFirstDayOfThisMonth());
    }

    private Product dtoToEntity(final ProductDTO dto) {
        final Product product = new Product();
        product.setId(dto.getId());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDebtorGroup(memberGroupService.findByName(dto.getDebtorGroup()));
        product.setCreditor(memberService.findByName(dto.getCreditor()));
        product.setCategory(Category.getByPolishName(dto.getCategory()));
        product.setCreationDate(Objects.isNull(dto.getCreationDate()) ? LocalDate.now() : LocalDate.parse(dto
                .getCreationDate()));
        return product;
    }

    private ProductDTO entityToDTO(final Product product) {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setDebtorGroup(product.getDebtorGroup().getName());
        productDTO.setCreditor(product.getCreditor().getName());
        productDTO.setCategory(product.getCategory().name());
        productDTO.setCreationDate(product.getCreationDate().toString());
        return productDTO;
    }

    private final ProductRepository productRepository;

    private final MemberGroupService memberGroupService;

    private final MemberService memberService;

    public ProductService(final ProductRepository productRepository,
                          final MemberGroupService memberGroupService,
                          final MemberService memberService) {
        this.productRepository = productRepository;
        this.memberGroupService = memberGroupService;
        this.memberService = memberService;
    }
}

