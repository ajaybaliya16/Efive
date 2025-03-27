package com.efive.formMaster.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "module")
public class ModuleForm {

	@Id
	@Column(name = "moduleid", nullable = false)
	private Integer moduleId;

	@Column(name = "modulename", nullable = false, length = 128)
	private String moduleName;

	@Column(name = "moduleshortname", nullable = false, length = 2)
	private String moduleShortName;

	@Column(name = "active", nullable = false)
	private Integer active;

	public ModuleForm() {
	}

	public ModuleForm(Integer moduleId, String moduleName, String moduleShortName, Integer active) {
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.moduleShortName = moduleShortName;
		this.active = active;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleShortName() {
		return moduleShortName;
	}

	public void setModuleShortName(String moduleShortName) {
		this.moduleShortName = moduleShortName;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Module{" + "moduleId=" + moduleId + ", moduleName='" + moduleName + '\'' + ", moduleShortName='"
				+ moduleShortName + '\'' + ", active=" + active + '\'' + '}';
	}
}