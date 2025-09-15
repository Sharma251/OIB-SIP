package com.onlineExamination.onlineExamination.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamination.onlineExamination.entity.Exam;
import com.onlineExamination.onlineExamination.entity.Option;
import com.onlineExamination.onlineExamination.entity.Question;
import com.onlineExamination.onlineExamination.repository.ExamRepository;
import com.onlineExamination.onlineExamination.repository.QuestionRepository;
import com.onlineExamination.onlineExamination.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

	    @Autowired
	    private ExamRepository examRepository;
	    @Autowired
	    private QuestionRepository questionRepository;
    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }
  

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();   // ✅ use findAll()
    }

    @Override
    public Exam getExamById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
    

    public List<Question> getQuestionsByExamId(Long examId) {
        Exam exam = examRepository.findById(examId)
                                  .orElseThrow(() -> new RuntimeException("Exam not found"));
        return questionRepository.findByExam(exam);
        }

    public int evaluateExam(Long examId, Map<Long, String> answers) {
        int score = 0;
        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Question q = questionRepository.findById(entry.getKey()).orElse(null);
            if (q != null && q.getOptions() != null) {
                for (Option option : q.getOptions()) {
                    if (option.getOptionText().equals(entry.getValue()) && option.isCorrect()) {
                        score++;
                        break;
                    }
                }
            }
        }
        return score;
    }


	@Override
	public List<Question> getQuestionsForExam(Long examId) {
		Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        return questionRepository.findByExam(exam);
	}
}