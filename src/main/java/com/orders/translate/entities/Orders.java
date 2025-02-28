package com.orders.translate.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Orders {

    @Id
    private Long orderId;

    private String date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private List<Products> products;

    public Orders() {
    }

    public Orders(Long orderId, List<Products> products, Users user, String date) {
        this.orderId = orderId;
        this.products = products;
        this.user = user;
        this.date = date;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}