package com.perfectcollection.billingsystem.repository;

import com.perfectcollection.billingsystem.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    @Query("{ 'createdAt' : ?0 }")
    Page<Transaction> findByCreatedAt(LocalDate createdAt, Pageable pageable);
}