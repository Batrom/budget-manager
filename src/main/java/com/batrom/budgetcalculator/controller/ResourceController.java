package com.batrom.budgetcalculator.controller;

import com.batrom.budgetcalculator.model.DebtDTO;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.model.ProductDTO;
import com.batrom.budgetcalculator.service.DebtService;
import com.batrom.budgetcalculator.service.ProductService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CommonsLog
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DebtService debtService;

    @GetMapping(value = "/getProducts")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        try {
            final List<ProductDTO> products = productService.getAllProductDTOList();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred during getting all products", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = "/saveProduct")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        try {
            final ProductDTO product = productService.save(productDTO);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred during saving product", e);
            return new ResponseEntity<>(productDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/deleteProduct")
    public ResponseEntity<ProductDTO> deleteProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.delete(productDTO);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred during deleting product", e);
            return new ResponseEntity<>(productDTO, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = "/editProduct")
    public ResponseEntity<ProductDTO> editProduct(@RequestBody ProductDTO productDTO) {
        try {
            final ProductDTO product = productService.updateProduct(productDTO);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred during editing product", e);
            return new ResponseEntity<>(productDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getDebts")
    public ResponseEntity<List<DebtDTO>> getDebts(@RequestParam(name = "user") String user) {
        try {
            final List<DebtDTO> debts = debtService.getDebtsByUser(user);
            return new ResponseEntity<>(debts, HttpStatus.OK);
        } catch (final Exception e) {
            log.error("Problem occurred during getting user debts", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

        }
    }
}
