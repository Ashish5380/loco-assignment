package com.loco.assignment.transactionsum.exception.handler;

public class ErrorResponseDto {
	private boolean isError;

	private String message;

	private int statusCode;

	public ErrorResponseDto() {
		this.isError = true;
		this.statusCode = 500;
	}

	public ErrorResponseDto(String message, int statusCode) {
		this();
		this.message = message;
		this.statusCode = statusCode;
	}
}
