package dev.davi.spring.security.jwt.controller;

import dev.davi.spring.security.jwt.model.dto.Login;
import dev.davi.spring.security.jwt.model.dto.Session;
import dev.davi.spring.security.jwt.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@Operation(summary = "Login de usuário", description = "Método para fazer login e lançar token de autenticação")
	@PostMapping("/login")
	public Session login(@RequestBody Login login) {
		return loginService.login(login);
	}

}
