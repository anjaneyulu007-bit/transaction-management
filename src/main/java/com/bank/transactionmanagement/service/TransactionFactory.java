package com.bank.transactionmanagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.bank.transactionmanagement.entity.Transaction;

public class TransactionFactory {
	public static Transaction createTransaction(Map<String, String> data) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(data.get("ACCOUNT_NUMBER"));
		transaction.setTrxAmount(Double.parseDouble(data.get("TRX_AMOUNT")));
		transaction.setDescription(data.get("DESCRIPTION"));
		transaction.setTrxDate(LocalDate.parse(data.get("TRX_DATE"), DateTimeFormatter.ISO_LOCAL_DATE));
		transaction.setTrxTime(LocalTime.parse(data.get("TRX_TIME"), DateTimeFormatter.ISO_LOCAL_TIME));
		transaction.setCustomerId(data.get("CUSTOMER_ID"));
		return transaction;
	}
}
