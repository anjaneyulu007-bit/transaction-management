package com.bank.transactionmanagement.batch;

import org.springframework.batch.item.ItemProcessor;

import com.bank.transactionmanagement.entity.Transaction;
import com.bank.transactionmanagement.service.TransactionFactory;

import java.util.Map;

public class TransactionItemProcessor implements ItemProcessor<Map<String, String>, Transaction> {
	@Override
	public Transaction process(Map<String, String> item) throws Exception {
		return TransactionFactory.createTransaction(item);
	}
}
