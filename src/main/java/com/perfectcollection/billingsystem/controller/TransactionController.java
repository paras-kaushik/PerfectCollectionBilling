package com.perfectcollection.billingsystem.controller;

import com.perfectcollection.billingsystem.model.Transaction;
import com.perfectcollection.billingsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String getAllTransactions(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactionList";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Transaction getTransactionById(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }
}