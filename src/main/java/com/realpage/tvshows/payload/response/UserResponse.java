package com.realpage.tvshows.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String username;
	
	private String email;
	
	private String role;
	

}
