package com.efive.formMaster.AnswerForm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerFormRepository extends JpaRepository<AnswerFormEntity, Long> {
	List<AnswerFormEntity> findByResponseId(Long responseId);
}