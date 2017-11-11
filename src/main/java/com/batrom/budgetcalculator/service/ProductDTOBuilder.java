package com.batrom.budgetcalculator.service;

import com.batrom.budgetcalculator.model.ProductDTO;
import com.batrom.budgetcalculator.model.User;
import com.batrom.budgetcalculator.model.UserGroup;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductDTOBuilder {

    public class Builder {
        private ProductDTO productDTO;

        Builder() {
            productDTO = new ProductDTO();
        }

        public Builder setId(final Long id) {
            this.productDTO.setId(id);
            return this;
        }

        public Builder setDescription(final String description) {
            this.productDTO.setDescription(description);
            return this;
        }

        public Builder setPrice(final BigDecimal price) {
            this.productDTO.setPrice(price);
            return this;
        }

        public Builder setCreationDate(final LocalDate creationDate) {
            this.productDTO.setCreationDate(creationDate.toString());
            return this;
        }

        public Builder setUserGroup(final UserGroup userGroup) {
            this.productDTO.setDebtorsGroup(userGroup.getName());
            return this;
        }

        public Builder setUser(final User user) {
            this.productDTO.setCreditor(user.getName());
            return this;
        }

        public ProductDTO build() {
            return this.productDTO;
        }
    }

    public Builder createInstance() {
        return new Builder();
    }
}