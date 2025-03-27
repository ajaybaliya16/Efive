package com.efive.formMaster.AnswerForm;

import java.util.List;

public class FormSubmissionRequest {
	private Long formId;
	private Long userId;
	private List<AnswerRequest> answers;

	// Getters and Setters
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<AnswerRequest> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerRequest> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "FormSubmissionRequest [formId=" + formId + ", userId=" + userId + ", answers=" + answers + "]";
	}
}

class AnswerRequest {
	private Long questionId;
	private String answerText;
	private Integer answerTypeId;
	private List<Long> optionIds; // Changed from Long to List<Long>

	// Getters and Setters
	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public Integer getAnswerTypeId() {
		return answerTypeId;
	}

	public void setAnswerTypeId(Integer answerTypeId) {
		this.answerTypeId = answerTypeId;
	}

	public List<Long> getOptionIds() {
		return optionIds;
	}

	public void setOptionIds(List<Long> optionIds) {
		this.optionIds = optionIds;
	}

	@Override
	public String toString() {
		return "AnswerRequest [questionId=" + questionId + ", answerText=" + answerText + ", answerTypeId="
				+ answerTypeId + ", optionIds=" + optionIds + "]";
	}
}