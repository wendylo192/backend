package com.realpage.tvshows.payload.response;

import com.realpage.tvshows.entity.User;
import com.realpage.tvshows.payload.request.UserRequest;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponse toResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();
	}

	public static User toDomain(UserRequest userRequest) {
		return User.builder()
				.username(userRequest.getUsername())
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())				
				.build();
	}

}
