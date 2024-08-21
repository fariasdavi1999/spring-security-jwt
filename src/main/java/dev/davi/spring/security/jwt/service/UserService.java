package dev.davi.spring.security.jwt.service;


import dev.davi.spring.security.jwt.config.encode.EncoderConfig;
import dev.davi.spring.security.jwt.exception.BusinessException;
import dev.davi.spring.security.jwt.exception.EntityNotFoundException;
import dev.davi.spring.security.jwt.model.User;
import dev.davi.spring.security.jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final EncoderConfig encoder;

	private final UserRepository userRepository;

	public UserService(EncoderConfig encoder, UserRepository userRepository) {
		this.encoder        = encoder;
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		try {
			String password = user.getPassword();
//		Encrypting
			user.setPassword(encoder.passwordEncoder().encode(password));
			userRepository.save(user);
		} catch (BusinessException e) {
			throw new BusinessException("Could not save user");
		}
	}

	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty())
			throw new EntityNotFoundException("No users found");
		return users;
	}

	public Optional<User> findUserByUsername(String username) {
		Optional<User> user = userRepository.findUserByUsername(username);
		if (user.isPresent())
			return user;
		throw new EntityNotFoundException("User not found by username: " + username);
	}
}
