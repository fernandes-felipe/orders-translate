package com.orders.translate.constrollers;

import com.orders.translate.constrollers.dto.APIResponseOrderDTO;
import com.orders.translate.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}/orders")
    public APIResponseOrderDTO listOrders(
            @PathVariable("orderId") Long orderId){
        return orderService.findByOrderId(orderId);
    }

    @GetMapping("/orders")
    public List<?> listAllOrders(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate){

        if (endDate == null && startDate == null){
            return orderService.findAll();
        }
        return orderService.findByDataBetween(startDate, endDate);
    }

}
