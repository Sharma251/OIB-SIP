package com.onlineExamination.onlineExamination.service;

import java.util.List;

import com.onlineExamination.onlineExamination.entity.User;

public interface UserService {
	   User saveUser(User user);
	    User findByUsername(String username);
	    List<User> findAllUsers();

}
