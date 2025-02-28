package com.orders.translate.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.orders.translate.entities.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;



    @BeforeEach
    void setup(){
        orderRepository.save(new Orders(1L,null,null,  "2025-02-01"));
        orderRepository.save(new Orders(2L,null,null,  "2025-02-03"));
        orderRepository.save(new Orders(3L,null,null,  "2025-03-05"));
    }
    @Test
    void shouldReturnOrderInRangeDate() {
        List<Orders> result = orderRepository.findByDataBetween("2024-02-05", "2025-02-01");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getDate()).isEqualTo("2025-02-01");
    }

    @Test
    void shouldReturnAllInserts() {

        List<Orders> result = orderRepository.findByDataBetween(null, null);
        assertThat(result).hasSize(3);
    }
}
