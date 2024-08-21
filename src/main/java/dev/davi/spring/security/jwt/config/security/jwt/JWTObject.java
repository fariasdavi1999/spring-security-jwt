package dev.davi.spring.security.jwt.config.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JWTObject {

	private String subject;

	private Date issuedAt;


	private Date expiration;

	private List<String> roles = new ArrayList<>();

}
