package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.Debt;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.model.ProductDTO;
import com.batrom.budgetcalculator.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private DebtService debtService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductDTO> getAllProductDTOList() {
        return getAllProducts().stream().map(this::mapToProductDTO).collect(Collectors.toList());
    }


    @Transactional
    public ProductDTO save(final ProductDTO productDTO) {
        final Product product = mapToProduct(productDTO);
        final List<Debt> debts = debtService.fillDebts(product);
        product.setDebts(debts);
        final Product savedProduct = productRepository.save(product);

        if (!Objects.isNull(savedProduct)) {
            return mapToProductDTO(savedProduct);
        } else {
            throw new RuntimeException();
        }
    }

    private ProductDTO mapToProductDTO(final Product product) {
        final ProductDTOBuilder builder = new ProductDTOBuilder();
        return builder.createInstance()
                      .setId(product.getId())
                      .setDescription(product.getDescription())
                      .setPrice(product.getPrice())
                      .setUserGroup(product.getDebtorsGroup())
                      .setUser(product.getCreditor())
                      .setCreationDate(product.getCreationDate())
                      .build();
    }

    public void delete(final ProductDTO productDTO) {
        productRepository.delete(productDTO.getId());
        if (!Objects.isNull(productRepository.findOne(productDTO.getId()))) throw new RuntimeException();
    }

    @Transactional
    public ProductDTO updateProduct(final ProductDTO productDTO) {
        return save(productDTO);
    }

    private Product mapToProduct(final ProductDTO productDTO) {
        final Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDebtorsGroup(userGroupService.findByName(productDTO.getDebtorsGroup()));
        product.setCreditor(userService.findByName(productDTO.getCreditor()));
        product.setCreationDate(LocalDate.now());

        Optional.ofNullable(productDTO.getId()).ifPresent(product::setId);

        return product;
    }
}
