package dev.davi.spring.security.jwt.config.security.jwt;

import dev.davi.spring.security.jwt.config.security.TokenConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/*
 Every request will be filtered
 */
public class JWTFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//obtem o token da request com AUTHORIZATION
		String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);
		//esta implementação só esta validando a integridade do token
		try {
			if (token != null && !token.isEmpty()) {
				JWTObject tokenObject = JWTCreator.reviewToken(TokenConfig.PREFIX, token);

				List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());

				UsernamePasswordAuthenticationToken userToken =
						new UsernamePasswordAuthenticationToken(
								tokenObject.getSubject(),
								null,
								authorities);

				SecurityContextHolder.getContext().setAuthentication(userToken);

			} else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			e.printStackTrace();
			e.getCause();
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}

	private List<SimpleGrantedAuthority> authorities(List<String> roles) {
		return roles.stream().map(SimpleGrantedAuthority::new)
		            .toList();
	}
}
