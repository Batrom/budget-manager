package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.ProductDTO;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CommonsLog
@RestController
@RequestMapping("/product")
public class ProductRestController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return get(productService::findProductsForView);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::save);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Long> delete(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::delete);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::update);
    }

    @PostMapping(value = "/updateCategory")
    public ResponseEntity<List<ProductDTO>> updateCategory(@RequestBody Map<String, List<ProductDTO>> productsByCategoryMap) {
        return execute(productsByCategoryMap, productService::updateCategory);
    }

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    private final ProductService productService;
}
