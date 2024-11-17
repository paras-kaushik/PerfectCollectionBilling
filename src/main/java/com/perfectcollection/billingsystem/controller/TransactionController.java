package com.perfectcollection.billingsystem.controller;

import com.perfectcollection.billingsystem.model.Transaction;
import com.perfectcollection.billingsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public String getTransactions(
            @RequestParam("date") Optional<String> date,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("page") Optional<Integer> page,
            Model model) {

        LocalDate filterDate = date.map(LocalDate::parse).orElse(LocalDate.now());
        String sortBy = sort.orElse("transactionNumber");
        int currentPage = page.orElse(0);
        int size = pageSize.orElse(5);

        Page<Transaction> transactionPage = transactionService.getTransactionsByDate(
                filterDate, PageRequest.of(currentPage, size, Sort.by(sortBy)));

        double totalSales = transactionPage.getContent().stream()
                .mapToDouble(Transaction::getTotalPrice)
                .sum();

        model.addAttribute("transactions", transactionPage.getContent());
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("date", filterDate.toString());
        model.addAttribute("sort", sortBy);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalSales", totalSales);

        return "transactionList";
    }

    @PostMapping("/create")
    @ResponseBody
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
    }

}