package com.onlineExamination.onlineExamination.serviceImpl;

import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.UserRepository;
import com.onlineExamination.onlineExamination.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {

	  @Autowired
	    private UserRepository userRepository;

	    @Override
	    public User saveUser(User user) {
	        return userRepository.save(user);
	    }

	    @Override
	    public User findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }

	    @Override
	    public List<User> findAllUsers() {
	        return userRepository.findAll();
	    }

	/*
	 * @Autowired private UserRepository userRepository;
	 * 
	 * User findByUsername(String username); // Authenticate user (manual login
	 * check) public User authenticate(String username, String password) { User user
	 * = userRepository.findByUsername(username);
	 * 
	 * if (user != null && user.getPassword().equals(password)) { return user; //
	 * valid login } return null; // invalid login }
	 * 
	 * // Update profile public User updateProfile(User updatedUser) { return
	 * userRepository.save(updatedUser); }
	 * 
	 * // Change password public boolean changePassword(Long userId, String
	 * oldPassword, String newPassword) { User user =
	 * userRepository.findById(userId).orElse(null); if (user != null &&
	 * user.getPassword().equals(oldPassword)) { user.setPassword(newPassword);
	 * userRepository.save(user); return true; } return false; }
	 */
}
    
