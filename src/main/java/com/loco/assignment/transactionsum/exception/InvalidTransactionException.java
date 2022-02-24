package com.loco.assignment.transactionsum.exception;

import com.loco.assignment.transactionsum.exception.base.ServiceException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class InvalidTransactionException extends ServiceException {
	public InvalidTransactionException(@NonNull String message, String... info) {
		super(message, info);
	}

	public InvalidTransactionException(@NonNull String message, @NonNull Exception exception, String... info) {
		super(message, exception, info);
	}

	public InvalidTransactionException(@NonNull String message, @NonNull HttpStatus httpStatus, String... info) {
		super(message, httpStatus, info);
	}

	public InvalidTransactionException(@NonNull String message, Exception exception, @NonNull HttpStatus httpStatus, String... info) {
		super(message, exception, httpStatus, info);
	}
}
