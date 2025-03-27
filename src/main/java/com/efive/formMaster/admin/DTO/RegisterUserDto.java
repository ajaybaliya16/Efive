package com.efive.formMaster.admin.DTO;

import java.security.Timestamp;
import java.time.LocalDate;

public class RegisterUserDto {

	private Long id;

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String fullName;
	private String contactNo;
	private String gender;
	private LocalDate validFrom;
	private LocalDate validTo;
	private Boolean isActive;
	private Long roleId;
	private String imageName;
	private String imagePath;
	private Long createdBy;
	private Timestamp createdOn;
	private Long updatedBy;
	private Timestamp updatedOn;
	private Boolean isDeleted;

	public RegisterUserDto() {
	}

	public RegisterUserDto(String firstName, String lastName, String email, String contactNo, String gender,
			LocalDate validFrom, LocalDate validTo, Long roleId, Boolean isActive, String imagePath, String imageName,
			Long id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNo = contactNo;
		this.gender = gender;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.roleId = roleId;
		this.isActive = isActive;
		this.imagePath = imagePath;
		this.imageName = imageName;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "RegisterUserDto [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", fullName=" + fullName + ", contactNo=" + contactNo + ", gender=" + gender
				+ ", validFrom=" + validFrom + ", validTo=" + validTo + ", isActive=" + isActive + ", roleId=" + roleId
				+ ", imageName=" + imageName + ", imagePath=" + imagePath + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", isDeleted=" + isDeleted
				+ "]";
	}

}
