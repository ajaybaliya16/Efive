package com.efive.formMaster.admin.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "forms")
public class FormEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long formId;

	@Column(name = "form_title", nullable = false)
	private String formTitle;

	@Column(name = "form_description")
	private String formDescription;

	@Column(name = "alias_name")
	private String aliasName;

	@Column(name = "module")
	private String module;

	@Column(name = "characteristic")
	private String characteristic;

	@Column(name = "sub_characteristic")
	private String subCharacteristic;

	@Column(name = "recurrence")
	private String recurrence;

	@Column(name = "start_month")
	private String startMonth;

	@Column(name = "compliance_period")
	private String compliancePeriod;

	@Column(name = "effective_date")
	private String effectiveDate;

	@Column(name = "isActive")
	private Boolean isActive;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "createdBy")
	private Long createdBy;

	@Column(name = "updatedBy")
	private Long updatedBy;

	@Column(name = "isdeleted")
	private Boolean isDeleted = Boolean.FALSE;

	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionEntity> questions;

	// Getters and Setters
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getFormTitle() {
		return formTitle;
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

	public List<QuestionEntity> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime localDateTime) {
		this.updatedAt = localDateTime;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}