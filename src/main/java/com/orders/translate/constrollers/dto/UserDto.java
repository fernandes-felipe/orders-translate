package com.orders.translate.constrollers.dto;

public record UserDto(
        Long userId,
        String name) {

    public UserDto(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @Override
    public Long userId() {
        return userId;
    }

    @Override
    public String name() {
        return name;
    }
}
