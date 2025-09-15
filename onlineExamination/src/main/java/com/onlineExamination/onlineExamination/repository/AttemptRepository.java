package com.onlineExamination.onlineExamination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineExamination.onlineExamination.entity.Attempt;
import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.User;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
	 List<Attempt> findByUser(User user);
	 List<Attempt> findByUser_Username(String username);
	 List<Attempt> findByExam(Exam exam);
}
