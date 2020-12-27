package com.ncpi.bank.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequest {
	@NotBlank
	private String userId;

	@NotBlank
	private String password;

}
