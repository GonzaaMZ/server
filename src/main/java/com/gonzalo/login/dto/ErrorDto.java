package com.gonzalo.login.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class ErrorDto {
	
	private String message; 
}
