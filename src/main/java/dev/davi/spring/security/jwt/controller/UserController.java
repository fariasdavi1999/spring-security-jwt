package dev.davi.spring.security.jwt.controller;

import dev.davi.spring.security.jwt.model.User;
import dev.davi.spring.security.jwt.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public void saveUser(@RequestBody User user) {
		userService.saveUser(user);
	}

}
