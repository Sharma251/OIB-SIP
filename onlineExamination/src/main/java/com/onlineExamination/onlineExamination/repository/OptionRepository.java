package com.onlineExamination.onlineExamination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineExamination.onlineExamination.entity.Option;
import com.onlineExamination.onlineExamination.entity.Question;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestion(Question question);
}
