package com.efive.formMaster.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mst_month")
public class Month {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String monthname;

	// Default constructor
	public Month() {
	}

	// Parameterized constructor
	public Month(Long id, String monthname) {
		this.id = id;
		this.monthname = monthname;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getmonthname() {
		return monthname;
	}

	public void setmonthname(String monthname) {
		this.monthname = monthname;
	}

	@Override
	public String toString() {
		return "Month{" + "id=" + id + ", monthname='" + monthname + '\'' + '}';
	}
}