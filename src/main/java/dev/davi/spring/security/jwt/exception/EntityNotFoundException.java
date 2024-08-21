package dev.davi.spring.security.jwt.exception;

public class EntityNotFoundException extends RuntimeException{
	public EntityNotFoundException(String message) {
		super(message);
	}
}
