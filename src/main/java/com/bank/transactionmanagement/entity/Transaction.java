package com.bank.transactionmanagement.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "trx_amount")
	private Double trxAmount;

	private String description;

	@Column(name = "trx_date")
	private LocalDate trxDate;

	@Column(name = "trx_time")
	private LocalTime trxTime;

	@Column(name = "customer_id")
	private String customerId;

	@Version
	private Integer version;

}
