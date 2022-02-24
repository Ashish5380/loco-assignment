package com.loco.assignment.transactionsum.service;

import com.loco.assignment.transactionsum.api.contracts.TransactionInfo;
import com.loco.assignment.transactionsum.api.contracts.TransactionSum;

import java.util.List;

public interface TransactionService {

	void saveTransaction(TransactionInfo transactionInfo, Long transactionId);

	List<Long> transactionByType(String type);

	TransactionSum sumTransaction(Long transactionId);

}
