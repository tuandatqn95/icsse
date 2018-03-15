package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.User;

@Service
public interface UserService {
	void save(User user);

	User findByUsername(String username);

}
