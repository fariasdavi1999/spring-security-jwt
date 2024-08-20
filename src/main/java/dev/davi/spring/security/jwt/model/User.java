package dev.davi.spring.security.jwt.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(length = 100, nullable = false)
	private String name;

	@Schema(description = "Nome de usuário para efetuar Login")
	@Column(length = 30, nullable = false, unique = true)
	private String username;

	@Column(length = 100, nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role_id")
	private List<String> roles = new ArrayList<>();

	public User(String username) {
		this.username = username;
	}
}
