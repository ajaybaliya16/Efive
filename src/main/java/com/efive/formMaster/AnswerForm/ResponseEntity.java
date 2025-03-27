package com.efive.formMaster.AnswerForm;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "mst_response")
public class ResponseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_id", nullable = false)
	private Long responseId;

	@Column(name = "form_id", nullable = false)
	private Long formId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "submittedAt", nullable = false)
	private Timestamp submittedAt;

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
	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

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

	public Timestamp getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(Timestamp submittedAt) {
		this.submittedAt = submittedAt;
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