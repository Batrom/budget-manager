package com.batrom.budgetcalculator;

import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.repository.ProductRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@CommonsLog
public class ExportProductsToFileTest {

    @Autowired
    private ProductRepository productRepository;

    private final static String DELIMITER = ";";

    @Test
    public void exportProducts() {
        final List<String> products = productRepository.findAll()
                                                       .stream()
                                                       .sorted(Comparator.comparing(Product::getId))
                                                       .map(product -> new StringBuilder().append(product.getId())
                                                                                          .append(DELIMITER)
                                                                                          .append(product.getDescription())
                                                                                          .append(DELIMITER)
                                                                                          .append(product.getPrice())
                                                                                          .append(DELIMITER)
                                                                                          .append(product.getCreditor().getName())
                                                                                          .append(DELIMITER)
                                                                                          .append(product.getDebtorsGroup().getName())
                                                                                          .append(DELIMITER)
                                                                                          .append(product.getCreationDate())
                                                                                          .toString())
                                                       .collect(Collectors.toList());
        final Path path = Paths.get("C://Users/Bartosz Romanowski/postgre_backup/products.txt");
        try {
            Files.write(path, products);
        } catch (final IOException e) {
            log.error("Error while writing to file", e);
        }
    }
}
