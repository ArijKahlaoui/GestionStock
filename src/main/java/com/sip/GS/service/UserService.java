package com.sip.GS.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sip.GS.dto.UserRegistrationDto;
import com.sip.GS.entities.User;


public interface UserService extends UserDetailsService{

	User save(UserRegistrationDto registrationDto);
	
}
