package com.perfectcollection.billingsystem.config;

import com.perfectcollection.billingsystem.model.Transaction;
import com.perfectcollection.billingsystem.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner runner(TransactionRepository transactionRepository) {
        return args -> {
            // Create some dummy purchases using Lombok's builder
//            Transaction.Purchase purchase1 = Transaction.Purchase.builder()
//                    .itemNumber("001")
//                    .itemName("Item A")
//                    .itemPrice(100.0)
//                    .itemQuantity(2)
//                    .itemTotalPrice(200.0)
//                    .build();
//
//            Transaction.Purchase purchase2 = Transaction.Purchase.builder()
//                    .itemNumber("002")
//                    .itemName("Item B")
//                    .itemPrice(150.0)
//                    .itemQuantity(1)
//                    .itemTotalPrice(150.0)
//                    .build();
//
//            Transaction transaction1 = Transaction.builder()
//                    .transactionNumber(1)
//                    .transactionName("John Doe")
//                    .transactionType("Sale")
//                    .remarks("First transaction")
//                    .shopname("Perfect Collection")
//                    .purchases(Arrays.asList(purchase1, purchase2))
//                    .totalItems(3)
//                    .totalPrice(350.0)
//                    .netPrice(409.5)
//                    .build();
//
//            // Save the transaction to the database
//            transactionRepository.save(transaction1);
        };
    }
}