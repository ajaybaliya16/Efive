package com.efive.formMaster.AnswerForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efive.formMaster.admin.Entity.FormSubmissionEntity;
import com.efive.formMaster.admin.repo.FormSubmissionRepository;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FormSubmissionServiceImpl implements FormSubmissionService {

	@Autowired
	private FormSubmissionRepository formSubmissionRepository;

	@Autowired
	private ResponseRepository responseRepository;

	@Autowired
	private AnswerFormRepository answerFormRepository;

	@Override
	@Transactional
	public String submitForm(FormSubmissionRequest request) {
		if (request.getUserId() == null) {
			throw new IllegalStateException("Logged-in user ID cannot be null.");
		}

		// Create and save ResponseEntity
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setFormId(request.getFormId());
		responseEntity.setUserId(request.getUserId());
		responseEntity.setSubmittedAt(new Timestamp(System.currentTimeMillis()));
		responseEntity.setCreatedBy(request.getUserId());
		responseEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		responseEntity.setUpdatedBy(request.getUserId());
		responseEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		responseEntity.setIsDeleted(false); // Ensure isDeleted is set
		ResponseEntity savedResponse = responseRepository.save(responseEntity);

		// Create and save FormSubmissionEntity
		FormSubmissionEntity formSubmissionEntity = new FormSubmissionEntity();
		formSubmissionEntity.setFormId(request.getFormId());
		formSubmissionEntity.setUserId(request.getUserId());
		formSubmissionEntity.setSubmittedAt(new Date(System.currentTimeMillis()));
		formSubmissionRepository.save(formSubmissionEntity);

		// Create and save AnswerFormEntities
		List<AnswerFormEntity> answerEntities = new ArrayList<>();
		for (AnswerRequest answerRequest : request.getAnswers()) {
			List<Long> optionIds = answerRequest.getOptionIds();
			if (optionIds != null && !optionIds.isEmpty()) {
				// For multi-select questions, create an entry for each optionId
				for (Long optionId : optionIds) {
					AnswerFormEntity answerEntity = new AnswerFormEntity();
					answerEntity.setResponseId(savedResponse.getResponseId());
					answerEntity.setQuestionId(answerRequest.getQuestionId());
					answerEntity.setAnswerText(answerRequest.getAnswerText());
					answerEntity.setAnswerTypeId(answerRequest.getAnswerTypeId());
					answerEntity.setOptionId(optionId); // Set single optionId
					answerEntity.setCreatedBy(request.getUserId());
					answerEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
					answerEntity.setUpdatedBy(request.getUserId());
					answerEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
					answerEntity.setIsDeleted(false); // Ensure isDeleted is set
					answerEntities.add(answerEntity);
				}
			} else {
				// For single-select or text-based questions with no optionIds
				AnswerFormEntity answerEntity = new AnswerFormEntity();
				answerEntity.setResponseId(savedResponse.getResponseId());
				answerEntity.setQuestionId(answerRequest.getQuestionId());
				answerEntity.setAnswerText(answerRequest.getAnswerText());
				answerEntity.setAnswerTypeId(answerRequest.getAnswerTypeId());
				answerEntity.setOptionId(null); // No optionId for non-select questions
				answerEntity.setCreatedBy(request.getUserId());
				answerEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				answerEntity.setUpdatedBy(request.getUserId());
				answerEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				answerEntity.setIsDeleted(false); // Ensure isDeleted is set
				answerEntities.add(answerEntity);
			}
		}

		answerFormRepository.saveAll(answerEntities);

		return "Form submitted successfully with Response ID: " + savedResponse.getResponseId();
	}
}