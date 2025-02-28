package com.orders.translate.repositories;

import com.orders.translate.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Products, Long> {
}
