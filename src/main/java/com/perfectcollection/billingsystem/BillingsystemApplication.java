package com.perfectcollection.billingsystem;

import com.perfectcollection.billingsystem.model.Transaction;
import com.perfectcollection.billingsystem.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BillingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingsystemApplication.class, args);
	}

}
