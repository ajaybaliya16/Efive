package com.efive.formMaster.admin.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_characteristics")
public class SubCharacteristic {

	@Id
	@Column(name = "subcharacteristicid", nullable = false)
	private Integer subCharacteristicId;

	@Column(name = "subcharacteristicname", nullable = false, length = 128)
	private String subCharacteristicName;

	@Column(name = "characteristicid")
	private Integer characteristicId;

	@Column(name = "active", nullable = false)
	private Integer active;

	// Default constructor
	public SubCharacteristic() {
	}

	// Parameterized constructor
	public SubCharacteristic(Integer subCharacteristicId, String subCharacteristicName, Integer characteristicId,
			Integer active) {
		this.subCharacteristicId = subCharacteristicId;
		this.subCharacteristicName = subCharacteristicName;
		this.characteristicId = characteristicId;
		this.active = active;
	}

	// Getters and Setters
	public Integer getSubCharacteristicId() {
		return subCharacteristicId;
	}

	public void setSubCharacteristicId(Integer subCharacteristicId) {
		this.subCharacteristicId = subCharacteristicId;
	}

	public String getSubCharacteristicName() {
		return subCharacteristicName;
	}

	public void setSubCharacteristicName(String subCharacteristicName) {
		this.subCharacteristicName = subCharacteristicName;
	}

	public Integer getCharacteristicId() {
		return characteristicId;
	}

	public void setCharacteristicId(Integer characteristicId) {
		this.characteristicId = characteristicId;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "SubCharacteristic{" + "subCharacteristicId=" + subCharacteristicId + ", subCharacteristicName='"
				+ subCharacteristicName + '\'' + ", characteristicId=" + characteristicId + ", active=" + active + '}';
	}
}
