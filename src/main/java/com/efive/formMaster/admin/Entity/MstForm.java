package com.efive.formMaster.admin.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_form")
public class MstForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "form_id", nullable = false)
	private Long id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "alias_name", nullable = false, length = 100)
	private String aliasName;

	@Column(name = "module_id", nullable = false)
	private Integer moduleId;

	@Column(name = "characteristic_id", nullable = false)
	private Integer characteristicId;

	@Column(name = "sub_characteristic_id", nullable = false)
	private Integer subCharacteristicId;

	@Column(name = "recurrence_id", nullable = false)
	private Integer recurrenceId;

	@Column(name = "mst_month_id", nullable = false)
	private Integer startMonth;

	@Column(name = "compliance_period", nullable = false)
	private Integer compliancePeriod;

	@Column(name = "effective_date", nullable = false)
	private LocalDateTime effectiveDate;

	@Column(name = "text_desc", nullable = false, length = 255)
	private String textEnglish;

	@Column(name = "createdBy", nullable = false)
	private long createdBy;

	@Column(name = "createdOn", nullable = false)
	private LocalDateTime createdOn;

	@Column(name = "updatedBy", nullable = false)
	private Integer updatedBy;

	@Column(name = "updatedOn", nullable = false)
	private LocalDateTime updatedOn;

	@Column(name = "isDeleted", nullable = false)
	private Boolean isDeleted = false;

	// Constructors, Getters, and Setters
	public MstForm() {
		this.createdOn = LocalDateTime.now();
		this.updatedOn = LocalDateTime.now();
		this.isDeleted = false;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getCharacteristicId() {
		return characteristicId;
	}

	public void setCharacteristicId(Integer characteristicId) {
		this.characteristicId = characteristicId;
	}

	public Integer getSubCharacteristicId() {
		return subCharacteristicId;
	}

	public void setSubCharacteristicId(Integer subCharacteristicId) {
		this.subCharacteristicId = subCharacteristicId;
	}

	public Integer getRecurrenceId() {
		return recurrenceId;
	}

	public void setRecurrenceId(Integer recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getCompliancePeriod() {
		return compliancePeriod;
	}

	public void setCompliancePeriod(Integer compliancePeriod) {
		this.compliancePeriod = compliancePeriod;
	}

	public LocalDateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTextEnglish() {
		return textEnglish;
	}

	public void setTextEnglish(String textEnglish) {
		this.textEnglish = textEnglish;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}