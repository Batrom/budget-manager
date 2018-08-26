package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.dto.ProductDTO;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/product")
public class ProductController extends BaseRestController {

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return get(productService::findAll);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::save);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<ProductDTO> delete(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::delete);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        return execute(productDTO, productService::update);
    }

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }
}
