package com.efive.formMaster.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mst_answer_type")
public class AnswerType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer answertypeid;

	private String answertypename;

	private Integer active;

	// Default constructor
	public AnswerType() {
	}

	// Parameterized constructor
	public AnswerType(Integer answertypeid, String answertypename, Integer active) {
		this.answertypeid = answertypeid;
		this.answertypename = answertypename;
		this.active = active;
	}

	// Getters and Setters
	public Integer getanswertypeid() {
		return answertypeid;
	}

	public void setanswertypeid(Integer answertypeid) {
		this.answertypeid = answertypeid;
	}

	public String getanswertypename() {
		return answertypename;
	}

	public void setanswertypename(String answertypename) {
		this.answertypename = answertypename;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "AnswerType{" + "answertypeid=" + answertypeid + ", answertypename='" + answertypename + '\''
				+ ", active=" + active + '}';
	}
}