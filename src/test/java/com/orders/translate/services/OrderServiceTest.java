package com.orders.translate.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testSaveOrder() {
        TranslateOrderDTO dto = new TranslateOrderDTO(
                1L,
                "Tester",
                10L,
                1L,
                new BigDecimal("100.00"),
                "2021-01-01"
                );

        orderService.saveOrder(dto);

        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        Users savedUser = userCaptor.getValue();
        assertEquals(dto.userId(), savedUser.getUserId());
        assertEquals(dto.userName(), savedUser.getName());

        ArgumentCaptor<List> productsCaptor = ArgumentCaptor.forClass(List.class);
        verify(productRepository, times(1)).saveAll(anyList());

        ArgumentCaptor<Orders> orderCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        Orders savedOrder = orderCaptor.getValue();
        assertEquals(dto.orderId(), savedOrder.getOrderId());
        assertEquals(dto.date(), savedOrder.getDate());
        assertEquals(savedUser, savedOrder.getUser());
    }

    @Test
    public void testFindByOrderIdWithSuccess() {

        Long orderId = 100L;

        Users user = new Users();
        user.setUserId(1L);
        user.setName("Tester");

        Products product = new Products();
        product.setProductId(10L);
        product.setValue(BigDecimal.valueOf(100.00));

        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setDate("2021-01-01");
        order.setUser(user);
        order.setProducts(List.of(product));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        APIResponseOrderDTO response = orderService.findByOrderId(orderId);

        assertEquals(user.getUserId(), response.userId());
        assertEquals(user.getName(), response.name());
        assertEquals(order.getOrderId(), response.orderId());
        assertEquals(order.getDate(), response.date());
        assertNotNull(response.products());
        assertEquals(1, response.products().size());

        ProductDTO productDTO = response.products().getFirst();
        assertEquals(product.getProductId(), productDTO.productId());
        assertEquals(product.getValue(), productDTO.value());
    }

    @Test
    public void testFindByOrderId_OrderNotFound() {
        Long orderId = 100L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.findByOrderId(orderId);
        });
        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    public void testFindByOrderId_UserNotFound() {
        Long orderId = 100L;

        Users user = new Users();
        user.setUserId(1L);
        user.setName("Tester");

        Products product = new Products();
        product.setProductId(10L);
        product.setValue(BigDecimal.valueOf(100.00));

        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setDate("2021-01-01");
        order.setUser(user);
        order.setProducts(List.of(product));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.findByOrderId(orderId);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testFindAll() {

        Products product = new Products();
        product.setProductId(10L);
        product.setValue(BigDecimal.valueOf(200.00));

        Orders order = new Orders();
        order.setOrderId(100L);
        order.setDate("2021-01-01");
        order.setProducts(List.of(product));

        Users user = new Users();
        user.setUserId(1L);
        user.setName("Tester");
        user.setOrders(List.of(order));

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<APIResponseDTO> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());

        APIResponseDTO responseDTO = result.get(0);
        assertEquals(user.getUserId(), responseDTO.userId());
        assertEquals(user.getName(), responseDTO.name());

        List<OrderDTO> ordersDTO = responseDTO.orders();
        assertNotNull(ordersDTO);
        assertEquals(1, ordersDTO.size());

        OrderDTO orderDTO = ordersDTO.get(0);
        assertEquals(order.getOrderId(), orderDTO.orderId());
        assertEquals(order.getDate(), orderDTO.date());

        List<ProductDTO> productsDTO = orderDTO.products();
        assertNotNull(productsDTO);
        assertEquals(1, productsDTO.size());

        ProductDTO productDTO = productsDTO.getFirst();
        assertEquals(product.getProductId(), productDTO.productId());
        assertEquals(product.getValue(), productDTO.value());

        verify(userRepository, times(1)).findAll();
}

    @Test
    public void testFindByDataBetween() {
        String startDate = "2021-01-01";
        String endDate = "2021-12-31";

        Users user = new Users();
        user.setUserId(1L);
        user.setName("Tester");

        Products product = new Products();
        product.setProductId(10L);
        product.setValue(BigDecimal.valueOf(100.00));

        Orders order = new Orders();
        order.setOrderId(100L);
        order.setDate( "2023-01-01");
        order.setUser(user);
        order.setProducts(Arrays.asList(product));

        when(orderRepository.findByDataBetween(startDate, endDate))
                .thenReturn(Arrays.asList(order));

        List<APIResponseOrderDTO> responseList = orderService.findByDataBetween(startDate, endDate);

        assertNotNull(responseList);
        assertEquals(1, responseList.size());

        APIResponseOrderDTO dto = responseList.get(0);
        assertEquals(user.getUserId(), dto.userId());
        assertEquals(user.getName(), dto.name());
        assertEquals(order.getOrderId(), dto.orderId());
        assertEquals(order.getDate(), dto.date());

        List<ProductDTO> products = dto.products();
        assertNotNull(products);
        assertEquals(1, products.size());

        ProductDTO prodDto = products.get(0);
        assertEquals(product.getProductId(), prodDto.productId());
        assertEquals(product.getValue(), prodDto.value());

        verify(orderRepository, times(1)).findByDataBetween(startDate, endDate);
    }

}