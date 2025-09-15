package com.onlineExamination.onlineExamination.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamination.onlineExamination.entity.Attempt;
import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.User;
import com.onlineExamination.onlineExamination.repository.AttemptRepository;
import com.onlineExamination.onlineExamination.service.AttemptService;

@Service
public class AttemptServiceImpl implements AttemptService {

    @Autowired
    private AttemptRepository attemptRepository;

    @Override
    public Attempt saveAttempt(Attempt attempt) {
        return attemptRepository.save(attempt);
    }

    @Override
    public List<Attempt> getAttemptsByUser(User user) {
        return attemptRepository.findByUser(user);
    }
    
	/*
	 * public List<Attempt> getAttemptsByUsername(String username) { return
	 * attemptRepository.findByUser_Username(username);
	 */
    
    @Override
    public List<Attempt> getAttemptsByExam(Exam exam) {
        return attemptRepository.findByExam(exam);
    }
    @Override
    public List<Attempt> getAllAttempts() {
        return attemptRepository.findAll();
    }

	@Override
	public List<Attempt> findByUser_Username(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
