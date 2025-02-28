package com.orders.translate.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {

    @Id
    private Long userId;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders;

    public Users() {
    }

    public Users(Long userId, String name, List<Orders> orders) {
        this.userId = userId;
        this.name = name;
        this.orders = orders;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
