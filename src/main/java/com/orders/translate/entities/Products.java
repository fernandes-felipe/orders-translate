package com.orders.translate.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Products {

    @Id
    private Long productId;


    @Column(name = "`value`")
    private BigDecimal value;

    @ManyToMany(mappedBy = "products")
    private List<Orders> orders;

    public Products() {
    }

    public Products(Long productId, BigDecimal value) {
        this.productId = productId;
        this.value = value;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
