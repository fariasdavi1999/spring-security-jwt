package dev.davi.spring.security.jwt.config.security.jwt;

import dev.davi.spring.security.jwt.config.security.jwt.token.TokenConfig;
import io.jsonwebtoken.JwtException;
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
import java.util.logging.Logger;

/*
 Every request will be filtered
 */
public class JWTFilter extends OncePerRequestFilter {
	private static final Logger log = Logger.getLogger(JWTFilter.class.getName());
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//obtem o token da request com AUTHORIZATION
		String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

		//esta implementação só esta validando a integridade do token
		try {
			if (token != null && !token.isEmpty() && token.startsWith(TokenConfig.PREFIX)) {
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
		} catch (JwtException e) {
			log.severe("JWT validation failed: " + e.getMessage());
			response.setHeader("Content-Type", "application/json");
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
		}
	}

	private List<SimpleGrantedAuthority> authorities(List<String> roles) {
		return roles.stream()
		            .map(SimpleGrantedAuthority::new)
		            .toList();
	}
}
