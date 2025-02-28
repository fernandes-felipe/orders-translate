package com.orders.translate.services;

import static org.mockito.Mockito.*;

import com.orders.translate.services.dto.TranslateOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TranslateServiceTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private TranslateService translateService;

    private String sampleFile;

    @BeforeEach
    void setUp() {
        // Criando um exemplo de arquivo fictício (cada linha representa um pedido)
        sampleFile = """
                0000000064                             Quintin Turcotte00000006880000000004      928.8920211221
                """;
    }

    @Test
    void shouldProcessFileCorrectly() throws Exception {

        translateService.translateFile(sampleFile);


        verify(orderService, times(1)).saveOrder(
                new TranslateOrderDTO(64L, "Quintin Turcotte", 688L, 4L, new BigDecimal("928.89"), "2021-12-21")
        );
    }
}

