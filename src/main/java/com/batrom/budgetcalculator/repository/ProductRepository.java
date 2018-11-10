package com.batrom.budgetcalculator.repository;

import com.batrom.budgetcalculator.enums.Category;
import com.batrom.budgetcalculator.model.Member;
import com.batrom.budgetcalculator.model.MemberGroup;
import com.batrom.budgetcalculator.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCreationDateGreaterThanEqualAndCreationDateLessThan(final LocalDate from, final LocalDate to);

    @Query("SELECT pr FROM Product pr WHERE ((pr.debtorGroup IN :memberGroups OR pr.creditor = :creditor) AND pr.creationDate >= :creationDate) OR " +
            "((pr.debtorGroup IN :memberGroups OR pr.creditor = :creditor) AND pr.category = :category)")
    List<Product> findProductsForView(@Param("memberGroups") final List<MemberGroup> memberGroups,
                                      @Param("creditor") final Member creditor,
                                      @Param("creationDate") final LocalDate creationDate,
                                      @Param("category") final Category category);

    List<Product> findProductsByDebtorGroupInOrCreditor(final List<MemberGroup> memberGroups, final Member creditor);

    @Query("SELECT pr FROM Product pr WHERE (pr.debtorGroup IN :memberGroups OR pr.creditor = :creditor) AND pr.creationDate >= :creationDate")
    List<Product> findProductsByDebtorGroupInOrCreditorAndCreationDateGreaterThanEqual(@Param("memberGroups") final List<MemberGroup> memberGroups,
                                                                                       @Param("creditor") final Member creditor,
                                                                                       @Param("creationDate") final LocalDate creationDate);

    @Modifying
    @Query("UPDATE Product pr SET pr.category = :category WHERE pr.id in :ids")
    void updateCategory(@Param("category") final Category category, @Param("ids") final List<Long> ids);
}
