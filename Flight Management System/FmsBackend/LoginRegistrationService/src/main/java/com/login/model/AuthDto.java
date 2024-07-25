package com.login.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
	@NotEmpty(message = "Please provide username and email...")
	private String userName;
	@NotEmpty(message = "Please give a password...")
	private String password;
}
