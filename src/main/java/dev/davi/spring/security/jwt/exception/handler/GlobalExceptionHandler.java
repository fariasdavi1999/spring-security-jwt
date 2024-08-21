package dev.davi.spring.security.jwt.exception.handler;

import dev.davi.spring.security.jwt.exception.BusinessException;
import dev.davi.spring.security.jwt.exception.EntityNotFoundException;
import dev.davi.spring.security.jwt.exception.detail.ExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionDetail> handleBusinessException(BusinessException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
		                     .body(new ExceptionDetail("Conflict! Consult the documentation!",
		                                               LocalDateTime.now(),
		                                               HttpStatus.BAD_REQUEST.value(),
		                                               exception.getMessage(),
		                                               Map.of("error", exception.getClass().toString())));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionDetail> handleEntityNotFoundException(EntityNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                     .body(new ExceptionDetail("Not Found",
		                                               LocalDateTime.now(),
		                                               HttpStatus.NOT_FOUND.value(),
		                                               exception.getMessage(),
		                                               Map.of("error", exception.getClass().toString())));
	}
}
