package dev.davi.spring.security.jwt.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session {
	private Long sessionId;
	private String token;
}
