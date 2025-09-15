package com.ATMInterface.ATMInterface.model;

import com.ATMInterface.ATMInterface.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create some sample users for testing
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUserId("user123");
            user1.setPin("1234");
            user1.setName("John Doe");
            user1.setBalance(1000.0);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUserId("user456");
            user2.setPin("5678");
            user2.setName("Jane Smith");
            user2.setBalance(500.0);
            userRepository.save(user2);

            User user3 = new User();
            user3.setUserId("user789");
            user3.setPin("9012");
            user3.setName("Bob Johnson");
            user3.setBalance(1500.0);
            userRepository.save(user3);
        }
    }
}
