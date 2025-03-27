package com.efive.formMaster.admin.DTO;

import java.util.List;

public class FormDTO {
	private Long FormId;
	private String formTitle;
	private String formDescription;
	private String aliasName;
	private String module;
	private String characteristic;
	private String subCharacteristic;
	private String recurrence;
	private String startMonth;
	private String compliancePeriod;
	private String effectiveDate;
	private Boolean isActive;
	private Long createdBy;
	private Long updatedBy;
	private Boolean isDeleted;
	private List<QuestionDTO> questions;

	// Getters and Setters

	public String getFormTitle() {
		return formTitle;
	}

	public Long getFormId() {
		return FormId;
	}

	public void setFormId(Long formId) {
		FormId = formId;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormDescription() {
		return formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getSubCharacteristic() {
		return subCharacteristic;
	}

	public void setSubCharacteristic(String subCharacteristic) {
		this.subCharacteristic = subCharacteristic;
	}

	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getCompliancePeriod() {
		return compliancePeriod;
	}

	public void setCompliancePeriod(String compliancePeriod) {
		this.compliancePeriod = compliancePeriod;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "FormDTO [formTitle=" + formTitle + ", formDescription=" + formDescription + ", aliasName=" + aliasName
				+ ", module=" + module + ", characteristic=" + characteristic + ", subCharacteristic="
				+ subCharacteristic + ", recurrence=" + recurrence + ", startMonth=" + startMonth
				+ ", compliancePeriod=" + compliancePeriod + ", effectiveDate=" + effectiveDate + ", isActive="
				+ isActive + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", isDeleted=" + isDeleted
				+ ", questions=" + questions + ", getFormTitle()=" + getFormTitle() + ", getFormDescription()="
				+ getFormDescription() + ", getAliasName()=" + getAliasName() + ", getModule()=" + getModule()
				+ ", getCharacteristic()=" + getCharacteristic() + ", getSubCharacteristic()=" + getSubCharacteristic()
				+ ", getRecurrence()=" + getRecurrence() + ", getStartMonth()=" + getStartMonth()
				+ ", getCompliancePeriod()=" + getCompliancePeriod() + ", getEffectiveDate()=" + getEffectiveDate()
				+ ", getIsActive()=" + getIsActive() + ", getCreatedBy()=" + getCreatedBy() + ", getUpdatedBy()="
				+ getUpdatedBy() + ", getIsDeleted()=" + getIsDeleted() + ", getQuestions()=" + getQuestions()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}