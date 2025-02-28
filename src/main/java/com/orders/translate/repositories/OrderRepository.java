package com.orders.translate.repositories;

import com.orders.translate.entities.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o WHERE " +
            "(:startDate IS NULL OR o.date >= :startDate) AND " +
            "(:endDate IS NULL OR o.date <= :endDate)")
    List<Orders> findByDataBetween(@Param("startDate") String startDate,
                                   @Param("endDate") String endDate);
}
