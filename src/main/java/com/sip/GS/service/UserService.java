package com.sip.GS.service;


import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sip.GS.entities.Role;
import com.sip.GS.entities.User;
import com.sip.GS.repositories.RoleRepository;
import com.sip.GS.repositories.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	 
	 @Autowired
	 public UserService(UserRepository userRepository,RoleRepository roleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) 
	 {
		 this.userRepository = userRepository;
		 this.roleRepository = roleRepository;
		 this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	 }
	
	 public User findUserByEmail(String email) {
		 return userRepository.findByEmail(email);
	}

	 
	 
	 public void saveUser(User user) {
		 
		 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		  user.setActive(0);
		  Role userRole = roleRepository.findByRole("USER");
		  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		  userRepository.save(user);
	}
}
