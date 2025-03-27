package com.efive.formMaster.complateForm;

import java.util.List;

public class FormPreviewDTO {
	private String formTitle;
	private String formDescription;
	private String completedDate;
	private List<QuestionAnswerDTO> questions;

	// Getters and Setters
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

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public List<QuestionAnswerDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionAnswerDTO> questions) {
		this.questions = questions;
	}
}