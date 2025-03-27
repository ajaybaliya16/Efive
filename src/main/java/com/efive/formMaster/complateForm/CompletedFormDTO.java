package com.efive.formMaster.complateForm;

import java.time.LocalDateTime;

public class CompletedFormDTO {
	private Long responseId;
	private String completedDate; // submittedAt formatted as "dd-MM-yyyy"
	private String formNumber; // e.g., "FORM-01"
	private String formName; // formTitle
	private String createdBy; // Username of the creator
	private Long formId;

	// Getters and Setters
	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}
}