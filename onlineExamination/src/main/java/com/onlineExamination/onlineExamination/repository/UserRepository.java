package com.onlineExamination.onlineExamination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlineExamination.onlineExamination.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
	User findByUsername(String username);
    User findByEmail(String email);

}
