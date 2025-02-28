package com.orders.translate.services;

import com.orders.translate.services.dto.TranslateOrderDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TranslateService {

    private final Logger logger = Logger.getLogger(TranslateService.class.getName());

   @Autowired
   private final OrderService orderService;

    public TranslateService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void translateFile(String file) throws Exception {
        if (file == null || file.isBlank()) {
            throw new IllegalArgumentException("The file cannot be null or empty.");
        }

        List<String> fileList = Arrays.stream(file.split("\n")).toList();
        try {
            logger.info("Opening file for read");

            for (int i = 0; i < fileList.size(); i ++){

                List<String> getUserId = getFileUserId(fileList);
                List<String> getUserName = getFileUserName(fileList);
                List<String> getOrderId = getFileOrderId(fileList);
                List<String> getProdId = getFileProdId(fileList);
                List<BigDecimal> getValue = getFileValue(fileList);
                List<String> getDate = getFileDate(fileList);


                TranslateOrderDTO dto = new TranslateOrderDTO(
                        (Long.valueOf(getUserId.get(i))),
                        getUserName.get(i),
                        (Long.valueOf(getOrderId.get(i))),
                        (Long.valueOf(getProdId.get(i))),
                        getValue.get(i),
                        getDate.get(i)
                );

                orderService.saveOrder(dto);
            }
        }catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        }
    }

    private static List<BigDecimal> getFileValue(List<String> fileList) {
        return fileList.stream()
                .map(value -> value.substring(76, 87))
                .map(num -> num.replaceAll("^\\s+|^0+", ""))
                .map(BigDecimal::new)
                .toList();
    }

    private static List<String> getFileProdId(List<String> fileList) {
        return fileList.stream()
                .map(prodId -> prodId.substring(66, 75))
                .map(num -> num.replaceAll("^\\s+|^0+", ""))
                .toList();
    }

    private static List<String> getFileOrderId(List<String> fileList) {
        return fileList.stream()
                .map(orderId -> orderId.substring(56, 65))
                .map(num -> num.replaceAll("^\\s+|^0+", ""))
                .toList();
    }

    private static List<String> getFileUserName(List<String> fileList) {
        return fileList.stream()
                .map(userName -> userName.substring(11, 55))
                .map(num -> num.replaceAll("^\\s+|^0+", ""))
                .toList();
    }

    private static List<String> getFileUserId(List<String> fileList) {
        return fileList.stream()
                .map(userId -> userId.substring(0, 10))
                .map(num -> num.replaceAll("^0+", ""))
                .toList();
    }

    private static List<String> getFileDate(List<String> fileList) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return fileList.stream()
                .map(date -> date.substring(87, 95))
                .map(num -> num.replaceAll("^\\s+|^0+", ""))
                .map(data -> LocalDate.parse(data, inputFormatter))
                .map(data -> data.format(outputFormatter))
                .toList();
    }
}
