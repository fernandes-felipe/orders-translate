package com.orders.translate.services.dto;

import java.math.BigDecimal;

public record TranslateOrderDTO(
        Long userId,
        String userName,
        Long orderId,
        Long prodId,
        BigDecimal value,
        String date
) {
    @Override
    public Long userId() {
        return userId;
    }

    @Override
    public String date() {
        return date;
    }

    @Override
    public BigDecimal value() {
        return value;
    }

    @Override
    public Long prodId() {
        return prodId;
    }

    @Override
    public Long orderId() {
        return orderId;
    }

    @Override
    public String userName() {
        return userName;
    }
}
