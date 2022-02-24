package com.loco.assignment.transactionsum.exception.handler;

import com.loco.assignment.transactionsum.exception.base.BaseException;
import com.loco.assignment.transactionsum.exception.base.FatalException;
import com.loco.assignment.transactionsum.exception.base.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler({FatalException.class})
	@ResponseBody
	public ResponseEntity<?> handleFatalExceptions(final FatalException e) {
		String stackTrace = BaseException.getStackTraceInStringFmt(e);
		String joinedInfo = String.join(", ", e.getInfo());

		log.error("Message : {}, info : {}, trace: {}", e.getMessage(), joinedInfo, stackTrace);
		ErrorResponseDto errorResponse = new ErrorResponseDto( e.getMessage(), getStatus(e).value());
		return new ResponseEntity<>(errorResponse, getStatus(e));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ServiceException.class})
	public ResponseEntity<?> handleServiceExceptions(final ServiceException e) {
		String stackTrace = BaseException.getStackTraceInStringFmt(e);
		String joinedInfo = String.join(", ", e.getInfo());

		log.error("Message : {}, info : {}, trace: {}", e.getMessage(), joinedInfo, stackTrace);

		ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), getStatus(e).value());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	private HttpStatus getStatus(BaseException exception) {
		return Optional.ofNullable(exception.getHttpStatus())
			.orElse(HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
