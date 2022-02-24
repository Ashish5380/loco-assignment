package com.loco.assignment.transactionsum.exception.base;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class FatalException extends  BaseException{
	protected FatalException(@NonNull String message, String... info) {
		super(message, info);
	}

	protected FatalException(@NonNull String message, @NonNull Exception exception, String... info) {
		super(message, exception, info);
	}

	protected FatalException(@NonNull String message, @NonNull HttpStatus httpStatus, String... info) {
		super(message, httpStatus, info);
	}

	protected FatalException(@NonNull String message, Exception exception, @NonNull HttpStatus httpStatus, String... info) {
		super(message, exception, httpStatus, info);
	}
}
