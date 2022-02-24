package com.loco.assignment.transactionsum.exception.base;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

public class BaseException extends RuntimeException{

	private Exception exception;

	private String[] info = new String[0];

	private HttpStatus httpStatus;

	protected BaseException(@NonNull String message, String... info) {
		this(message, null, HttpStatus.INTERNAL_SERVER_ERROR, info);
	}

	protected BaseException(@NonNull String message,@NonNull Exception exception, String... info) {
		this(message, exception, HttpStatus.INTERNAL_SERVER_ERROR, info);
	}

	protected BaseException(@NonNull String message,@NonNull  HttpStatus httpStatus, String... info) {
		this(message, null, httpStatus, info);
	}

	protected BaseException(@NonNull String message,Exception exception, @NonNull HttpStatus httpStatus, String... info) {
		super(message, exception);
		this.info = info == null ? new String[0] : info;
		this.exception = exception;
		this.httpStatus = httpStatus;
	}

	public String[] getInfo() {
		return info;
	}

	public Exception getException() {
		return exception;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public static String getStackTraceInStringFmt(Exception exceptionTrace) {
		if (Objects.isNull(exceptionTrace)) {
			return "EMPTY";
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		exceptionTrace.printStackTrace(printWriter);
		printWriter.close();
		return writer.toString();
	}
}
