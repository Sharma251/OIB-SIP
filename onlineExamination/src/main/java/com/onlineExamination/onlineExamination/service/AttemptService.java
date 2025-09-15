package com.onlineExamination.onlineExamination.service;

import java.util.List;

import com.onlineExamination.onlineExamination.entity.Attempt;
import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.User;

public interface AttemptService {
	 Attempt saveAttempt(Attempt attempt);
	 List<Attempt> getAttemptsByUser(User user);
	 List<Attempt> getAttemptsByExam(Exam exam);
	 List<Attempt> getAllAttempts();
	 List<Attempt> findByUser_Username(String username);  // ✅ correct

}
