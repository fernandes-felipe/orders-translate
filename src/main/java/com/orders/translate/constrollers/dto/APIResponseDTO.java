package com.orders.translate.constrollers.dto;

import java.util.List;

public record APIResponseDTO(
        Long userId,
        String name,
        List<OrderDTO> orders
        ) {
        public APIResponseDTO(Long userId, String name, List<OrderDTO> orders) {
                this.userId = userId;
                this.name = name;
                this.orders = orders;
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
        public List<OrderDTO> orders() {
                return orders;
        }
}
