package com.realpage.tvshows.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realpage.tvshows.entity.ERole;
import com.realpage.tvshows.entity.Role;
import com.realpage.tvshows.entity.User;
import com.realpage.tvshows.payload.request.UserRequest;
import com.realpage.tvshows.payload.response.MessageResponse;
import com.realpage.tvshows.payload.response.UserMapper;
import com.realpage.tvshows.payload.response.UserResponse;
import com.realpage.tvshows.service.IRoleService;
import com.realpage.tvshows.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tvshows/users")
@Slf4j
public class UserController {

	@Value("${tvshows.app.success}")
	private String success;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder encoder;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<UserResponse>> getAll() {
		List<User> users = userService.getAllUsers();
		List<UserResponse> userResponses = new ArrayList<>();

		if (Objects.isNull(users) || users.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		users.forEach(user -> {
			UserResponse userResponse = UserMapper.toResponse(user);
			userResponse.setRole(user.getRole().getName().name());
			userResponses.add(userResponse);			
		});

		return new ResponseEntity<>(userResponses, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")	
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
		if (userService.existsByUsername(userRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken"));
		}

		if (userService.existsByEmail(userRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use"));
		}

		// Create new user's account
		userRequest.setPassword(encoder.encode(userRequest.getPassword()));
		User user = UserMapper.toDomain(userRequest);
		user.setRole(getRole(userRequest));
		
		final User userToSave = userService.saveUser(user);

		UserResponse userResponse = UserMapper.toResponse(userToSave);
		log.info("User {} was added", userResponse.toString() );
		
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable(value = "id") Long id) {
		final User user = userService.getUserById(id);

		if (Objects.isNull(user)) {
			return ResponseEntity.notFound().build();
		}

		UserResponse userResponse = UserMapper.toResponse(user);
		log.info("User {} was queried", userResponse.toString() );
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest) {
		User user = userService.getUserById(userRequest.getId());
		User userToSave = UserMapper.toDomain(userRequest);

		userToSave.setId(user.getId());
		userToSave.setPassword(user.getPassword());
		userToSave.setRole(getRole(userRequest));
		
		final User userSaved = userService.saveUser(userToSave);
		
		UserResponse userResponse = UserMapper.toResponse(userSaved);
		log.info("User {} was updated. New data was the following: {}", userResponse.toString(), userRequest.toString() );

		return new ResponseEntity<>(userSaved, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestBody UserRequest userRequest) {
		userService.deleteUser(userRequest.getId());

		log.info("User with id {} was deleted", userRequest.getId());
		return new ResponseEntity<>(success, HttpStatus.OK);
	}
	
	private Role getRole(UserRequest userRequest) {
		String roleName = userRequest.getRole();
		
		return roleService.getRoleByName(Objects.nonNull(ERole.valueOf(roleName)) ? 
				ERole.valueOf(roleName): ERole.USER);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/allRoles")
	public ResponseEntity<List<Role>> getRoles() {
		List<Role> roles = roleService.getAllRoles();

		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
}
