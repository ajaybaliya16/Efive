package com.efive.formMaster.admin.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "characteristics")
public class Characteristics {

	@Id
	@Column(name = "characteristicid", nullable = false)
	private Integer characteristicId;

	@Column(name = "characteristicname", nullable = false, length = 128)
	private String characteristicName;

	@Column(name = "active", nullable = false)
	private Integer active;

	// Default constructor
	public Characteristics() {
	}

	// Parameterized constructor
	public Characteristics(Integer characteristicId, String characteristicName, Integer active) {
		this.characteristicId = characteristicId;
		this.characteristicName = characteristicName;
		this.active = active;
	}

	// Getters and Setters
	public Integer getCharacteristicId() {
		return characteristicId;
	}

	public void setCharacteristicId(Integer characteristicId) {
		this.characteristicId = characteristicId;
	}

	public String getCharacteristicName() {
		return characteristicName;
	}

	public void setCharacteristicName(String characteristicName) {
		this.characteristicName = characteristicName;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Characteristics{" + "characteristicId=" + characteristicId + ", characteristicName='"
				+ characteristicName + '\'' + ", active=" + active + '}';
	}
}
