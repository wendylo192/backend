package com.realpage.tvshows.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequest {
	
	private Long id;
	
	private String username;

	@Email
	private String email;

	private String role;

	@Size(min = 6, max = 40)
	private String password;

}
