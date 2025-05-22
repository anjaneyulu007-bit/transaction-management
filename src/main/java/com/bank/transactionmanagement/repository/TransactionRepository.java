package com.bank.transactionmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.transactionmanagement.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query("SELECT t FROM Transaction t WHERE " + "(:customerId IS NULL OR t.customerId = :customerId) OR "
			+ "(:accountNumber IS NULL OR t.accountNumber = :accountNumber) OR "
			+ "(:description IS NULL OR t.description LIKE %:description%)")
	Page<Transaction> searchTransactions(@Param("customerId") String customerId,
			@Param("accountNumber") String accountNumber, @Param("description") String description, Pageable pageable);
}
