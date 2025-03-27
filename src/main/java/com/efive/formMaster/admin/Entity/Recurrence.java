package com.efive.formMaster.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recurrence")
public class Recurrence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recurrenceId;

	private String name;

	private Integer active;

	// Default constructor
	public Recurrence() {
	}

	// Parameterized constructor
	public Recurrence(Integer recurrenceId, String name, Integer active) {
		this.recurrenceId = recurrenceId;
		this.name = name;
		this.active = active;
	}

	// Getters and Setters
	public Integer getRecurrenceId() {
		return recurrenceId;
	}

	public void setRecurrenceId(Integer recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Recurrence{" + "recurrenceId=" + recurrenceId + ", name='" + name + '\'' + ", active=" + active + '}';
	}
}