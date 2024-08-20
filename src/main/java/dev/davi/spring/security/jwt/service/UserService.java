package dev.davi.spring.security.jwt.service;


import dev.davi.spring.security.jwt.config.encode.EncoderConfig;
import dev.davi.spring.security.jwt.model.User;
import dev.davi.spring.security.jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

	private final Logger logger = Logger.getLogger(UserService.class.toString());
	private final EncoderConfig encoder;

	private final UserRepository userRepository;

	public UserService(EncoderConfig encoder, UserRepository userRepository) {
		this.encoder        = encoder;
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		String password = user.getPassword();
//		Encrypting
		user.setPassword(encoder.passwordEncoder().encode(password));
		userRepository.save(user);
		logger.info("Saving user " + user);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User findUserByUsername(String username) {

		return userRepository.findUserByUsername(username);
	}
}
