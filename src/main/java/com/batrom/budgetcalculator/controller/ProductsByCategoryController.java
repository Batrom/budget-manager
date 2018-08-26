package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.ProductsByCategoryDTO;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/productsByCategory")
public class ProductsByCategoryController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ProductsByCategoryDTO>> getAll(@RequestParam(name = "member") final String member) {
        return get(member, productService::findProductsToAssignCategory);
    }

    @PostMapping(value = "/updateAll")
    public ResponseEntity<List<ProductsByCategoryDTO>> updateAll(@RequestBody List<ProductsByCategoryDTO> productsByCategoryDTOList) {
        return execute(productsByCategoryDTOList, productService::updateCategory);
    }

    private final ProductService productService;

    @Autowired
    public ProductsByCategoryController(final ProductService productService) {
        this.productService = productService;
    }
}
