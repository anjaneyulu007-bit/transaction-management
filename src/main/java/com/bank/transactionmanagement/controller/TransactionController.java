package com.bank.transactionmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.transactionmanagement.dto.TransactionDTO;
import com.bank.transactionmanagement.entity.Transaction;
import com.bank.transactionmanagement.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public Page<Transaction> getTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String description,
            Pageable pageable) {
        return transactionService.searchTransactions(customerId, accountNumber, description, pageable);
    }

    @PutMapping("/{id}")
	public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id,
			@RequestBody TransactionDTO transactionDTO) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }
}
