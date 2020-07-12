package com.realpage.tvshows.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realpage.tvshows.repository.RoleRepository;
import com.realpage.tvshows.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tvshows")
public class HomeController {
	
	@Value("${tvshows.app.welcome}")
	private String welcomeMessage;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/home")
	public ResponseEntity<String> allAccess() {;
		return new ResponseEntity<>(welcomeMessage, HttpStatus.OK);
	}
	
}
