package dev.davi.spring.security.jwt.service;

import dev.davi.spring.security.jwt.config.encode.EncoderConfig;
import dev.davi.spring.security.jwt.config.security.TokenConfig;
import dev.davi.spring.security.jwt.config.security.jwt.JWTCreator;
import dev.davi.spring.security.jwt.config.security.jwt.JWTObject;
import dev.davi.spring.security.jwt.model.User;
import dev.davi.spring.security.jwt.model.dto.Login;
import dev.davi.spring.security.jwt.model.dto.Session;
import dev.davi.spring.security.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class LoginService {
	private final EncoderConfig encoder;

	private final UserService userService;

	@Autowired
	public LoginService(EncoderConfig encoder, UserService userService) {
		this.encoder     = encoder;
		this.userService = userService;
	}

	public Session login(Login login) {
		User user = userService.findUserByUsername(login.getUsername());
		if (user != null) {
			boolean passwordOk = encoder.passwordEncoder().matches(login.getPassword(), user.getPassword());
			if (!passwordOk) {
				throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
			}
			//Estamos enviando um objeto Sessão para retornar mais informações do usuário
			Session session = new Session();
			session.setSessionId(user.getId().longValue());

			JWTObject jwtObject = new JWTObject();
			jwtObject.setSubject(user.getUsername());
			jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
			jwtObject.setExpiration((new Date(System.currentTimeMillis() + TokenConfig.EXPIRATION)));
			jwtObject.setRoles(user.getRoles());

			session.setToken(JWTCreator.createToken(TokenConfig.PREFIX, jwtObject));
			return session;
		} else {
			throw new RuntimeException("Erro ao tentar fazer login");
		}
	}
}
