package com.orders.translate.constrollers.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long productId,
        BigDecimal value
) {
    public ProductDTO(Long productId, BigDecimal value) {
        this.productId = productId;
        this.value = value;
    }

    @Override
    public Long productId() {
        return productId;
    }

    @Override
    public BigDecimal value() {
        return value;
    }
}
