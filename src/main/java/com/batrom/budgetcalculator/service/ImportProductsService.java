package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import com.batrom.budgetcalculator.model.Product;
import com.batrom.budgetcalculator.repository.MemberGroupRepository;
import com.batrom.budgetcalculator.repository.MemberRepository;
import com.batrom.budgetcalculator.repository.ProductRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@CommonsLog
@Service
public class ImportProductsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @PostConstruct
    public void init() {
        importProducts();
    }

    public void importProducts() {
        final List<Member> members = memberRepository.findAll();
        final List<MemberGroup> memberGroups = memberGroupRepository.findAll();
        final Converter converter = new Converter(members, memberGroups);
        try {
            final Path path = Paths.get(getClass().getClassLoader().getResource("products.txt").toURI());
            Files.lines(path)
                 .map(line -> {
                     final String[] row = line.split(";");
                     return converter.convert(row);
                 })
                 .sorted(Comparator.comparing(Product::getCreationDate))
                 .forEach(productRepository::save);
        } catch (final IOException | URISyntaxException e) {
            log.error("Error while reading file", e);
        }
    }

    private class Converter {
        private final List<Member> members;
        private final List<MemberGroup> memberGroups;

        private Converter(final List<Member> members, final List<MemberGroup> memberGroups) {
            this.members = members;
            this.memberGroups = memberGroups;
        }

        private Product convert(final String[] row) {
            final Product product = new Product();
            product.setDescription(row[1]);
            product.setPrice(new BigDecimal(row[2]).setScale(2, RoundingMode.FLOOR));
            product.setCreditor(findMemberByName(row[3]));
            product.setDebtorGroup(findMemberGroupByName(row[4]));
            product.setCreationDate(LocalDate.parse(row[5]));
            product.setCategory(Category.NONE);
            return product;
        }

        private Member findMemberByName(final String memberName) {
            return members.stream()
                          .filter(member -> member.getName().equals(memberName))
                          .findFirst()
                          .orElseThrow(() -> new RuntimeException("No member found for member name: " + memberName));
        }

        private MemberGroup findMemberGroupByName(final String memberGroupName) {
            return memberGroups.stream()
                               .filter(memberGroup -> memberGroup.getName().equals(memberGroupName))
                               .findFirst()
                               .orElseThrow(() -> new RuntimeException("No member group found for member group name: " + memberGroupName));
        }
    }
}
