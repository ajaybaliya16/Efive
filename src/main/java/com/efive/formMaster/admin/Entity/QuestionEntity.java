package com.efive.formMaster.admin.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@DynamicUpdate
@Table(name = "questions")
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;

	@ManyToOne
	@JoinColumn(name = "form_id", nullable = false)
	private FormEntity form;

	@Column(name = "question_text", nullable = false)
	private String questionText;

	@Column(name = "question_type", nullable = false)
	private String questionType;

	@Column(name = "is_required")
	private boolean isRequired;

	@Column(name = "position", nullable = false)
	private int position;

	@Column(name = "description")
	private String description;

	@Column(name = "question_label", nullable = false)
	private String questionLabel;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "createdBy")
	private Long createdBy;

	@Column(name = "updatedBy")
	private Long updatedBy;

	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;

	@Column(name = "validation") // Single column to store validation value (e.g., "0", "1", "2", "3", or null)
	private String validation;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OptionEntity> options;

	// Getters and Setters

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public FormEntity getForm() {
		return form;
	}

	public void setForm(FormEntity form) {
		this.form = form;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Boolean isRequired() {
		return isRequired;
	}

	public void setRequired(Boolean required) {
		isRequired = required;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<OptionEntity> getOptions() {
		return options;
	}

	public void setOptions(List<OptionEntity> options) {
		this.options = options;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionLabel() {
		return questionLabel;
	}

	public void setQuestionLabel(String questionLabel) {
		this.questionLabel = questionLabel;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime localDateTime) {
		this.createdAt = localDateTime;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

}