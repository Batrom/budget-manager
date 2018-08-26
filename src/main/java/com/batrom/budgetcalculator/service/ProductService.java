package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.dto.ProductDTO;
import com.batrom.budgetcalculator.dto.ProductsByCategoryDTO;
import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.repository.ProductRepository;
import com.batrom.budgetcalculator.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToFunction;
import static com.batrom.budgetcalculator.util.FunctionUtils.wrapToPredicate;
import static java.util.stream.Collectors.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class ProductService {

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                                .stream()
                                .map(this::entityToDTO)
                                .collect(toList());
    }

    public ProductDTO save(final ProductDTO dto) {
        return wrapToFunction(this::dtoToEntity).andThen(productRepository::save)
                                                .andThen(this::entityToDTO)
                                                .apply(dto);
    }

    public ProductDTO update(final ProductDTO dto) {
        return save(dto);
    }

    public void updateCategory(final List<ProductsByCategoryDTO> productsByCategoryDTOList) {
        productRepository.saveAll(productsByCategoryDTOList.stream()
                                                           .map(dto -> dto.getProducts()
                                                                          .stream()
                                                                          .peek(productDTO -> productDTO.setCategory(dto
                                                                                  .getCategory()))
                                                                          .collect(toList()))
                                                           .flatMap(Collection::stream)
                                                           .map(this::dtoToEntity)
                                                           .collect(toList()));
    }

    public void delete(final ProductDTO dto) {
        productRepository.deleteById(dto.getId());
    }

    @Transactional
    public List<ProductsByCategoryDTO> findProductsToAssignCategory(final String memberName) {
        return wrapToFunction(this::findProductsByDebtorOrCreditorName).andThen(this::productsToProductsByCategoryDTO)
                                                                       .andThen(this::fillWithEmptyArrays)
                                                                       .apply(memberName);
    }

    private List<ProductsByCategoryDTO> fillWithEmptyArrays(final List<ProductsByCategoryDTO> dtoList) {
        return Stream.of(dtoList, Arrays.stream(Category.values())
                                        .filter(category -> !dtoList.stream()
                                                                    .map(ProductsByCategoryDTO::getCategory)
                                                                    .collect(toList())
                                                                    .contains(category.name()))
                                        .map(ProductsByCategoryDTO::new)
                                        .collect(toList()))
                     .flatMap(Collection::stream)
                     .collect(toList());
    }

    public List<Product> findProductsBetweenDates(final LocalDate from, final LocalDate to) {
        return productRepository.findProductsByCreationDateGreaterThanEqualAndCreationDateLessThan(from, to);
    }

    public List<Product> findProductsByDebtorOrCreditor(final Member member) {
        final List<MemberGroup> memberGroups = memberGroupService.findMemberGroupsByMember(member);
        return isEmpty(memberGroups) ?
                productRepository.findProductsByCreditor(member) :
                productRepository.findProductsByDebtorGroupInOrCreditor(memberGroups, member);
    }

    public List<Product> findProductsByDebtorOrCreditorName(final String memberName) {
        return wrapToFunction(memberService::findByName).andThen(this::findProductsByDebtorOrCreditor)
                                                        .apply(memberName);
    }

    public List<Product> findProductsByDebtorOrCreditorFromThisMonth(final Member member) {
        final List<MemberGroup> memberGroups = memberGroupService.findMemberGroupsByMember(member);
        return isEmpty(memberGroups) ?
                productRepository.findProductsByCreditorAndCreationDateGreaterThanEqual(member, DateUtils
                        .getFirstDayOfThisMonth()) :
                productRepository.findProductsByDebtorGroupInOrCreditorAndCreationDateGreaterThanEqual(memberGroups,
                        member, DateUtils
                                .getFirstDayOfThisMonth());
    }

    private boolean hasNoCategory(final Product product) {
        return Objects.equals(product.getCategory(), Category.NONE);
    }

    private boolean isThisMonth(final Product product) {
        return DateUtils.isThisMonth(product.getCreationDate());
    }

    private Product dtoToEntity(final ProductDTO dto) {
        final Product product = new Product();
        product.setId(dto.getId());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDebtorGroup(memberGroupService.findByName(dto.getDebtorGroup()));
        product.setCreditor(memberService.findByName(dto.getCreditor()));
        product.setCategory(StringUtils.isNotEmpty(dto.getCategory()) ? Category.valueOf(dto.getCategory()) :
                Category.NONE);
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

    private List<ProductsByCategoryDTO> productsToProductsByCategoryDTO(final List<Product> products) {
        return products.stream()
                       .filter(wrapToPredicate(this::hasNoCategory).or(this::isThisMonth))
                       .collect(groupingBy(Product::getCategory, mapping(this::entityToDTO, toList())))
                       .entrySet()
                       .stream()
                       .map(ProductsByCategoryDTO::new)
                       .collect(toList());
    }

    private final ProductRepository productRepository;

    private final MemberGroupService memberGroupService;

    private final MemberService memberService;

    @Autowired
    public ProductService(final ProductRepository productRepository, final MemberGroupService memberGroupService, final
    MemberService memberService) {
        this.productRepository = productRepository;
        this.memberGroupService = memberGroupService;
        this.memberService = memberService;
    }
}
