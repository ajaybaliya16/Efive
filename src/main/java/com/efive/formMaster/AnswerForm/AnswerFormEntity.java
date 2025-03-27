package com.efive.formMaster.AnswerForm;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "mst_answerform")
public class AnswerFormEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id", nullable = false)
	private Long answerId;

	@Column(name = "response_id", nullable = false)
	private Long responseId;

	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "answer_text", length = 255)
	private String answerText;

	@Column(name = "answertype_id", nullable = false)
	private Integer answerTypeId;

	@Column(name = "option_id")
	private Long optionId;

	@Column(name = "createdBy", nullable = false)
	private Long createdBy;

	@Column(name = "createdOn", nullable = false)
	private Timestamp createdOn;

	@Column(name = "updatedBy", nullable = false)
	private Long updatedBy;

	@Column(name = "updatedOn", nullable = false)
	private Timestamp updatedOn;

	@Column(name = "isDeleted", nullable = false)
	private Boolean isDeleted = false;

	// Getters and Setters
	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

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

	public Long getOptionId() {
		return optionId;
	}

	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}