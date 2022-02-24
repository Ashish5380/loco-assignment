package com.loco.assignment.transactionsum.api;

import com.loco.assignment.transactionsum.api.contracts.TransactionInfo;
import com.loco.assignment.transactionsum.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	/**
	 * API for creating transaction with id
	 * @param transactionInfo : object for creating transaction
	 * @param transactionId : id that you want to assign to a transaction
	 * @return Http status created
	 */
	@RequestMapping(path = "/{transactionId}",method = RequestMethod.PUT)
	public ResponseEntity<?> save(@RequestBody TransactionInfo transactionInfo,@PathVariable("transactionId") Long transactionId){
		transactionService.saveTransaction(transactionInfo, transactionId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * API for filtering transaction by type
	 * @param transactionType : transaction type to filter
	 * @return  : list of transaction ids
	 */
	@RequestMapping(path = "/type/{transactionType}", method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionByType(@PathVariable("transactionType")String transactionType){
		return new ResponseEntity<>(transactionService.transactionByType(transactionType),HttpStatus.OK);
	}

	/**
	 *  API for calculating sum of all transitively linked transactions
	 * @param transactionId : parent transaction id
	 * @return  : sum of all transactions
	 */
	@RequestMapping(path = "/sum/{transactionId}", method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionSum(@PathVariable("transactionId")String transactionId){
		return new ResponseEntity<>(transactionService.sumTransaction(Long.valueOf(transactionId)),HttpStatus.OK);
	}
}
