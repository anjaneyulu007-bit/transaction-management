package com.bank.transactionmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.transactionmanagement.dto.TransactionDTO;
import com.bank.transactionmanagement.entity.Transaction;
import com.bank.transactionmanagement.exception.ConcurrentModificationException;
import com.bank.transactionmanagement.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Page<Transaction> searchTransactions(String customerId, String accountNumber, String description,
			Pageable pageable) {
		return transactionRepository.searchTransactions(customerId, accountNumber, description, pageable);
	}

	@Transactional
	public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {
		try {
			Transaction transaction = transactionRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Transaction not found"));
			transaction.setDescription(transactionDTO.getDescription());
			return transactionRepository.save(transaction);
		} catch (ObjectOptimisticLockingFailureException e) {
			throw new ConcurrentModificationException(
					"Concurrent modification detected for transaction ID: " + id + ". Please retry.");
		}
	}
}
