package dev.davi.spring.security.jwt.config.security.jwt;

import dev.davi.spring.security.jwt.config.security.TokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.List;

/*
	Responsable to create or take the token that was created in a previous moment, convert into an object and validate
	 the credential
 */
public class JWTCreator {
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String ROLES_AUTHORITIES = "authorities";

	private JWTCreator() {
		throw new IllegalStateException("Cannot initialize");
	}


	public static String createToken(String prefix, JWTObject jwtObject)
			throws
			JwtException {
		String token = Jwts.builder()
		                   .header()
		                   .add("typ", "JWT")
		                   .and()
		                   .subject(jwtObject.getSubject())
		                   .issuedAt(jwtObject.getIssuedAt())
		                   .expiration(jwtObject.getExpiration())
		                   .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
		                   .signWith(TokenConfig.getSecretKey()).compact();
		return prefix + " " + token;
	}

	public static JWTObject reviewToken(String prefix, String token)
			throws
			JwtException {

		JWTObject object = new JWTObject();
		token = token.replace(prefix + " ", "");
		Claims claims = Jwts.parser()
		                    .verifyWith(TokenConfig.getSecretKey())
		                    .build()
		                    .parseSignedClaims(token)
		                    .getPayload();

		object.setSubject(claims.getSubject());
		object.setIssuedAt(claims.getIssuedAt());
		object.setExpiration(claims.getExpiration());
		object.setRoles(claims.get(ROLES_AUTHORITIES, List.class));
		return object;
	}

	private static List<String> checkRoles(List<String> roles) {
		return roles.stream()
		            .map(s -> "ROLE_".concat(s.replace("ROLE_", ""))).toList();
	}

}
