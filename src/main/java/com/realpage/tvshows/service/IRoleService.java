package com.realpage.tvshows.service;

import java.util.List;

import com.realpage.tvshows.entity.ERole;
import com.realpage.tvshows.entity.Role;

public interface IRoleService {
	List<Role> getAllRoles();
	Role getRoleById(Integer roleId);
	Role getRoleByName(ERole name);
	Role saveRole(Role user);
	void deleteRole(Integer roleId);	
}
