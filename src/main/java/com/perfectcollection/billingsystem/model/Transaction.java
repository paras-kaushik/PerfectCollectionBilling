package com.perfectcollection.billingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private int transactionNumber;
    private String transactionName;
    private String transactionType;
    private String remarks;
    private String shopname;
    @Field("purchases")
    private List<Purchase> purchases;
    private int totalItems;
    private double totalPrice;
    private double netPrice;
    @CreatedDate
    private LocalDate createdAt; // Automatically populated

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Purchase {
        private String itemNumber;
        private String itemName;
        private double itemPrice;
        private int itemQuantity;
        private double itemTotalPrice;
    }
}