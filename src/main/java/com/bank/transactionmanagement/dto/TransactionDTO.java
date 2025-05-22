package com.bank.transactionmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class TransactionDTO {
	private Long id;
	private String accountNumber;
	private Double trxAmount;
	private String description;
	private LocalDate trxDate;
	private LocalTime trxTime;
	private String customerId;
	private Integer version;
}
