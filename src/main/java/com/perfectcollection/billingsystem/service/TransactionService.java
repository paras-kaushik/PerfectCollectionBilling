package com.perfectcollection.billingsystem.service;

import com.perfectcollection.billingsystem.model.Transaction;
import com.perfectcollection.billingsystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<Transaction> getTransactionsByDate(LocalDate date, Pageable pageable) {
        return transactionRepository.findByCreatedAt(date, pageable);
    }

    public double calculateTotalSales(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getTotalPrice)
                .sum();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(String id) {
        transactionRepository.deleteById(id);
    }
}