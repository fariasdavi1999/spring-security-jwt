package dev.davi.spring.security.jwt.config.security;

import dev.davi.spring.security.jwt.config.security.jwt.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	private static final String MANAGER = "MANAGER";
	private static final String USER = "USER";

	private static final String[] OPENAPI_WHITELIST = {
			"/v2/api-docs/**",
			"/v3/api-docs/**",
			"/swagger-ui/**",
			"/swagger-ui.html/**",
			"/swagger-ui.html#/**",
			"/swagger-resources/",
			"/swagger-resources/**",
			"/configuration/ui/",
			"/configuration/security/",
			"/webjars/**"
	};

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http)
			throws Exception {
		http.headers(HeadersConfigurer::disable)
		    .csrf(CsrfConfigurer::disable)
		    .cors(CorsConfigurer::disable)
		    .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
		    .authorizeHttpRequests(
				    authorization ->
						    authorization.requestMatchers(OPENAPI_WHITELIST).permitAll()
						                 .requestMatchers("/h2-console/**").permitAll()
						                 .requestMatchers("/").permitAll()
						                 .requestMatchers(HttpMethod.POST, "/login").permitAll()
						                 .requestMatchers(HttpMethod.POST, "/users").permitAll()
//						                 AUTH
                                         .requestMatchers(HttpMethod.DELETE).hasRole(MANAGER)
                                         .requestMatchers(HttpMethod.GET, "/users").hasAnyRole(USER, MANAGER)
                                         .requestMatchers("/managers").hasAnyRole(MANAGER)
                                         .requestMatchers("/getAll").hasAnyRole(MANAGER)
                                         .anyRequest().authenticated())
		    .sessionManagement(
				    httpSecuritySessionManagementConfigurer ->
						    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
								    SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
