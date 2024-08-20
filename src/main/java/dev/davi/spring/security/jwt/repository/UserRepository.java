package dev.davi.spring.security.jwt.repository;

import dev.davi.spring.security.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
	User findUserByUsername(@Param("username") String username);

	boolean existsByUsername(String username);
}
