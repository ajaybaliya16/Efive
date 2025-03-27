package com.efive.formMaster.admin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efive.formMaster.admin.Entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

	void deleteByFormFormId(Long formId); // Deletes questions where form.formId matches
	
	List<QuestionEntity> findByFormFormIdAndIsDeletedFalse(Long formId);
	}