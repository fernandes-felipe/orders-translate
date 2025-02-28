package com.orders.translate.constrollers.dto;

import java.util.List;

public record APIResponseOrderDTO(
            Long userId,
            String name,
            Long orderId,
            String date,
            List<ProductDTO> products
    ) {
        public APIResponseOrderDTO(Long userId, String name, Long orderId, String date, List<ProductDTO> products) {
        this.userId = userId;
        this.name = name;
        this.orderId = orderId;
        this.date = date;
        this.products = products;
        }

    @Override
    public Long userId() {
        return userId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
        public Long orderId() {
            return orderId;
        }

        @Override
        public List<ProductDTO> products() {
            return products;
        }

        @Override
        public String date() {
            return date;
        }

}
