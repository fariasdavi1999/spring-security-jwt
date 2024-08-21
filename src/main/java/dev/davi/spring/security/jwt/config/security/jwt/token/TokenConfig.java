package dev.davi.spring.security.jwt.config.security.jwt.token;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "spring.security.config")
public class TokenConfig {

	public static String PREFIX;
	private static final SecretKey SECRET_KEY = Jwts.SIG.HS512.key().build();

	public static Long EXPIRATION;

	public static SecretKey getSecretKey() {
		return SECRET_KEY;
	}

	public void setPrefix(String prefix) {
		PREFIX = prefix;
	}

	public void setExpiration(Long expiration) {
		EXPIRATION = expiration;
	}


}
