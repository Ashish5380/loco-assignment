package com.loco.assignment.transactionsum.service.impl;

import com.loco.assignment.transactionsum.api.contracts.TransactionInfo;
import com.loco.assignment.transactionsum.api.contracts.TransactionSum;
import com.loco.assignment.transactionsum.domain.models.Transaction;
import com.loco.assignment.transactionsum.domain.repository.TransactionRepository;
import com.loco.assignment.transactionsum.exception.InvalidTransactionException;
import com.loco.assignment.transactionsum.exception.TransactionDoesNotExistException;
import com.loco.assignment.transactionsum.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final TransactionRepository transactionRepository;

	@Override
	@Transactional
	public synchronized void saveTransaction(TransactionInfo transactionInfo, Long transactionId){
		if(Objects.nonNull(transactionInfo)){
			Optional<Transaction> transaction = transactionRepository.findById(transactionId);
			if(transaction.isPresent()) {
				transaction.get().setAmount(transactionInfo.getAmount());
				transaction.get().setType(transactionInfo.getType());
				transaction.get().setParent(Objects.isNull(transactionInfo.getParentId()) ? null :
					transactionRepository.findById(transactionInfo.getParentId()).get());

			}else{
				Transaction newTransaction = Transaction.builder()
					.id(transactionId)
					.amount(transactionInfo.getAmount())
					.parent(Objects.isNull(transactionInfo.getParentId()) ? null :
						transactionRepository.findById(transactionInfo.getParentId()).get())
					.type(transactionInfo.getType())
					.build();
				transactionRepository.save(newTransaction);
			}
		}else{
			throw new InvalidTransactionException("Transaction information came to be null", HttpStatus.BAD_REQUEST);
		}
	}

	public List<Long> transactionByType(String type){
		List<Transaction> transactionList = transactionRepository.findByType(type);
		return transactionList.stream().map(Transaction::getId).collect(Collectors.toList());
	}

	@Override
	public TransactionSum sumTransaction(Long transactionId){
		Transaction actualTransaction = transactionRepository.getById(transactionId);
		if(Objects.nonNull(actualTransaction)) {
			List<Long> transactionIdList = new ArrayList<>();
			traverseTransactionAndFetchChildren(actualTransaction, transactionIdList);
			List<Transaction> transactionList = transactionRepository.findByIdIn(transactionIdList);
			// CAN BE REMOVED IF JUST WANT SUM OF CHILD TRANSACTION
			transactionList.add(actualTransaction);
			Double totalAmount = 0D;
			for(Transaction transaction : transactionList){
				totalAmount += transaction.getAmount();
			}
			return TransactionSum.builder().sum(totalAmount).build();
		}else {
			throw new TransactionDoesNotExistException("Provided transaction id " + transactionId + " does not exist");
		}
	}

	public void traverseTransactionAndFetchChildren(Transaction node, List<Long> childrenIds) {
		int size = node.getChildren().size();

		if(size > 0){
			for(Transaction childNode: node.getChildren()){
				childrenIds.add(childNode.getId());
				traverseTransactionAndFetchChildren(childNode, childrenIds);
			}
		}
	}


}
