package com.ncpi.bank.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;

	public JwtResponse(String accessToken, String username, String email) {
		this.token = accessToken;
		this.username = username;
		this.email = email;
	}
}
