package com.perfectcollection.billingsystem.repository;

import com.perfectcollection.billingsystem.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}