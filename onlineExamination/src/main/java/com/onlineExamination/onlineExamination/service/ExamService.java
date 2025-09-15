package com.onlineExamination.onlineExamination.service;

import java.util.List;
import java.util.Map;

import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.Question;

public interface ExamService {
	  List<Exam> getAllExams();
	    Exam getExamById(Long id);
	    Exam saveExam(Exam exam);
	    void deleteExam(Long id);
		List<Question> getQuestionsForExam(Long examId);
		int evaluateExam(Long examId, Map<Long, String> answers);
	}