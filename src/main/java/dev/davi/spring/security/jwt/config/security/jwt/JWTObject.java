package dev.davi.spring.security.jwt.config.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class JWTObject {
	@Setter
	private String subject;

	@Setter
	private Date issuedAt;

	@Setter
	private Date expiration;

	private List<String> roles = new ArrayList<>();

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
