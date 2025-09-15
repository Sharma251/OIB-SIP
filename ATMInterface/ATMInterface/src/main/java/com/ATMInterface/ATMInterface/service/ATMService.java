package com.ATMInterface.ATMInterface.service;

import com.ATMInterface.ATMInterface.model.Transaction;
import com.ATMInterface.ATMInterface.model.User;
import com.ATMInterface.ATMInterface.repository.TransactionRepository;
import com.ATMInterface.ATMInterface.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ATMService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public User authenticateUser(String userId, String pin) {
        return userRepository.findByUserIdAndPin(userId, pin);
    }

    @Transactional
    public User withdraw(String userId, double amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            
            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setType("WITHDRAW");
            transaction.setAmount(amount);
            transaction.setTimestamp(LocalDateTime.now());
            transactionRepository.save(transaction);
            
            return user;
        }
        return null;
    }

    @Transactional
    public User deposit(String userId, double amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
            
            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setType("DEPOSIT");
            transaction.setAmount(amount);
            transaction.setTimestamp(LocalDateTime.now());
            transactionRepository.save(transaction);
            
            return user;
        }
        return null;
    }

    @Transactional
    public User transfer(String fromUserId, String toUserId, double amount) {
        User fromUser = userRepository.findById(fromUserId).orElse(null);
        User toUser = userRepository.findById(toUserId).orElse(null);
        
        if (fromUser != null && toUser != null && fromUser.getBalance() >= amount) {
            fromUser.setBalance(fromUser.getBalance() - amount);
            toUser.setBalance(toUser.getBalance() + amount);
            
            userRepository.save(fromUser);
            userRepository.save(toUser);
            
            Transaction transaction = new Transaction();
            transaction.setUserId(fromUserId);
            transaction.setType("TRANSFER");
            transaction.setAmount(amount);
            transaction.setTargetUserId(toUserId);
            transaction.setTimestamp(LocalDateTime.now());
            transactionRepository.save(transaction);
            
            return fromUser;
        }
        return null;
    }

    public List<Transaction> getTransactionHistory(String userId) {
        return transactionRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
