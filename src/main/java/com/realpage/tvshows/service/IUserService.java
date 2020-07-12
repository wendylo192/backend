package com.realpage.tvshows.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.realpage.tvshows.entity.User;

public interface IUserService {
	List<User> getAllUsers();
	User getUserById(Long id);
	User saveUser(User user);
	void deleteUser(Long id);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
