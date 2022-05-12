package com.sip.GS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sip.GS.entities.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	
}
