package dev.davi.spring.security.jwt.controller;

import dev.davi.spring.security.jwt.model.User;
import dev.davi.spring.security.jwt.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {

	private final UserService userService;

	public WelcomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String welcome() {
		return "Welcome!";
	}

	@GetMapping("/getAll")
	public List<User> getAll() {
		return userService.getUsers();
	}

	@GetMapping("/users")
	public String welcomeUser() {
		return "Welcome User!";
	}

	@GetMapping("/managers")
	public String welcomeAdmin() {
		return "Welcome Admin!";
	}

}
