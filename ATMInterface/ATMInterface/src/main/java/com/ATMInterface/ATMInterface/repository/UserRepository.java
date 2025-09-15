package com.ATMInterface.ATMInterface.repository;

import com.ATMInterface.ATMInterface.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserIdAndPin(String userId, String pin);
    User findByUserId(String userId);
}
