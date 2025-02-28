package com.orders.translate.services;

import com.orders.translate.constrollers.dto.APIResponseDTO;
import com.orders.translate.constrollers.dto.APIResponseOrderDTO;
import com.orders.translate.constrollers.dto.OrderDTO;
import com.orders.translate.constrollers.dto.ProductDTO;
import com.orders.translate.entities.Orders;
import com.orders.translate.entities.Products;
import com.orders.translate.entities.Users;
import com.orders.translate.exceptions.OrderNotFoundException;
import com.orders.translate.repositories.OrderRepository;
import com.orders.translate.repositories.ProductRepository;
import com.orders.translate.repositories.UserRepository;
import com.orders.translate.services.dto.TranslateOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Logger logger = Logger.getLogger(OrderService.class.getName());

    @Autowired
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository repository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = repository;
    }

    @Transactional
    public void saveOrder(TranslateOrderDTO dto){
        Users entityUser = new Users();

        entityUser.setUserId(dto.userId());
        entityUser.setName(dto.userName());
        userRepository.save(entityUser);

        Products entityProducts = new Products();
        entityProducts.setProductId(dto.prodId());
        entityProducts.setValue(dto.value());
        productRepository.saveAll(List.of(entityProducts));

        Orders entityOrders = new Orders();
        entityOrders.setOrderId(dto.orderId());
        entityOrders.setDate(dto.date());
        entityOrders.setUser(entityUser);
        entityOrders.setProducts(List.of(entityProducts));
        orderRepository.save(entityOrders);

        logger.info("Order saved in database");
    }

    public APIResponseOrderDTO findByOrderId(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        System.out.println(orderId);
        Users user = userRepository.findById(order.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new APIResponseOrderDTO(
                user.getUserId(),
                user.getName(),
                order.getOrderId(),
                order.getDate(),
                order.getProducts().stream().map(product ->
                        new ProductDTO(product.getProductId(), product.getValue())
                ).toList());
    }

    public List<APIResponseDTO> findAll() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> new APIResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getOrders().stream().map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getDate(),
                        order.getProducts().stream().map(products -> new ProductDTO(
                                products.getProductId(),
                                products.getValue()
                        )).collect(Collectors.toList())
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public List<APIResponseOrderDTO> findByDataBetween(String startDate, String endDate) {
        List<Orders> orders = orderRepository.findByDataBetween(startDate, endDate);

        return orders.stream().map(order ->
                new APIResponseOrderDTO(
                        order.getUser().getUserId(),
                        order.getUser().getName(),
                        order.getOrderId(),
                        order.getDate(),
                        order.getProducts().stream().map(product ->
                                new ProductDTO(product.getProductId(), product.getValue())
                        ).toList()                )
        ).toList();
    }
}
