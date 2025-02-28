package com.orders.translate.constrollers.dto;

import java.util.List;

public record OrderDTO(
        Long orderId,
        String date,
        List<ProductDTO> products
        ) {

    public OrderDTO(Long orderId, String date, List<ProductDTO> products) {
        this.orderId = orderId;
        this.date = date;
        this.products = products;
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
