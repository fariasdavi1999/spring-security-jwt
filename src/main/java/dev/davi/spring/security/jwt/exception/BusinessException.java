package dev.davi.spring.security.jwt.exception;

public class BusinessException extends RuntimeException{
	public BusinessException(String message) {
		super(message);
	}
}
