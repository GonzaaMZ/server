package com.gonzalo.login.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gonzalo.login.config.UserAuthProvider;
import com.gonzalo.login.dto.CredentialsDto;
import com.gonzalo.login.dto.SignUpDto;
import com.gonzalo.login.dto.UserDto;
import com.gonzalo.login.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
    
    private UserService userService;
    private UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto = userService.login(credentialsDto);

        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register ")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.register(signUpDto);
        userDto.setToken(userAuthProvider.createToken(userDto.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
        
    }
    

}
