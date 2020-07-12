package com.realpage.tvshows.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realpage.tvshows.entity.ERole;
import com.realpage.tvshows.entity.Role;
import com.realpage.tvshows.repository.RoleRepository;

@Service
public class RoleService implements IRoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		roleRepository.findAll().forEach(role -> roles.add(role));

		return roles;
	}

	@Override
	public Role getRoleById(Integer roleId) {
		Optional<Role> roleOpt = roleRepository.findById(roleId);

		if (roleOpt.isPresent()) {
			return roleOpt.get();
		}
		return null;
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void deleteRole(Integer id) {
		Role role = getRoleById(id);
		roleRepository.delete(role);
	}

	@Override
	public Role getRoleByName(ERole name) {
		Optional<Role> roleOpt = roleRepository.findByName(name);

		if (roleOpt.isPresent()) {
			return roleOpt.get();
		}
		return null;
	}
}
