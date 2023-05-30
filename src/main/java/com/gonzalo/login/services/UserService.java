package com.gonzalo.login.services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gonzalo.login.dto.CredentialsDto;
import com.gonzalo.login.dto.SignUpDto;
import com.gonzalo.login.dto.UserDto;
import com.gonzalo.login.entities.User;
import com.gonzalo.login.exceptions.AppException;
import com.gonzalo.login.mappers.UserMapper;
import com.gonzalo.login.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private  UserRepository userRepository;
	private  UserMapper userMapper;
	private  PasswordEncoder passwordEncoder;
	
	public UserDto findByLogin(String login) {
		
		User user = userRepository.findByLogin(login)
				.orElseThrow(() -> new AppException("Unknownuser", HttpStatus.NOT_FOUND));
		return userMapper.toUserDto(user);
	}
	
	public UserDto login(CredentialsDto credentialsDto) {
			
		User user = userRepository.findByLogin(credentialsDto.getLogin())
		.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
			
		if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
			return userMapper.toUserDto(user);
		}
		throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
	}
	
	public UserDto register(SignUpDto userDto) {
		Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());
		
		if(optionalUser.isPresent()) {
			throw new AppException("Username already exist", HttpStatus.BAD_REQUEST);
		}
		
		User user = userMapper.signUpToUser(userDto);
		
		user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
		
		User savedUser = userRepository.save(user);
		return userMapper.toUserDto(user);
	}
	
}








