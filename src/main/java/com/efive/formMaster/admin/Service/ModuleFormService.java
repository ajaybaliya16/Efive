package com.efive.formMaster.admin.Service;

import java.util.List;

import com.efive.formMaster.admin.Entity.Characteristics;
import com.efive.formMaster.admin.Entity.ModuleForm;

public interface ModuleFormService {
	
	List<ModuleForm> findAllModules();

	List<Characteristics> findCharacteristicsByModuleId(Integer moduleId);

}
