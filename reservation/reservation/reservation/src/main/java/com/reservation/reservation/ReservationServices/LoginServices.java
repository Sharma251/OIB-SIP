package com.reservation.reservation.ReservationServices;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservation.reservation.model.Login;
import com.reservation.reservation.repository.LoginRepository;

@Service
public class LoginServices {

    @Autowired
    private LoginRepository loginRepository;

    public String isValidUser(String username, String password) {
    	Login login = loginRepository.findByUsernameAndPassword(username, password);
        return (login != null) ? login.getRole() : "";
    }
    
    public Optional<Login> findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }
    
    public Login updateUser(Login user) {
        return loginRepository.save(user);
    }
    
    public Login createUser(Login user) {
        return loginRepository.save(user);
    }
}
