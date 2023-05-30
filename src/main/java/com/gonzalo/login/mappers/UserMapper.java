package com.gonzalo.login.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gonzalo.login.dto.SignUpDto;
import com.gonzalo.login.dto.UserDto;
import com.gonzalo.login.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toUserDto(User user);

	@Mapping(target = "password", ignore = true)
	User signUpToUser(SignUpDto userDto);
	
	
}
