package com.onlineExamination.onlineExamination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineExamination.onlineExamination.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long>{
	 // Using standard JpaRepository methods: findAll(), findById(), save(), deleteById()
}
