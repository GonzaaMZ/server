package com.gonzalo.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gonzalo.login.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByLogin(String login);
}
