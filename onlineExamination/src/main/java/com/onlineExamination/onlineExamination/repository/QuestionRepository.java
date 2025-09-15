package com.onlineExamination.onlineExamination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByExam(Exam exam);
}
