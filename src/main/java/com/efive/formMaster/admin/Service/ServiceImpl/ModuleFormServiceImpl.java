package com.efive.formMaster.admin.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efive.formMaster.admin.Entity.Characteristics;
import com.efive.formMaster.admin.Entity.ModuleForm;
import com.efive.formMaster.admin.Service.ModuleFormService;
import com.efive.formMaster.admin.repo.CharacteristicsRepository;
import com.efive.formMaster.admin.repo.ModuleFormRepository;

@Service
public class ModuleFormServiceImpl implements ModuleFormService {

	private final ModuleFormRepository moduleRepository;

	private final CharacteristicsRepository characteristicsRepository;

	@Autowired
	public ModuleFormServiceImpl(ModuleFormRepository moduleRepository,
			CharacteristicsRepository characteristicsRepository) {
		this.moduleRepository = moduleRepository;
		this.characteristicsRepository = characteristicsRepository;
	}

	@Override
	public List<ModuleForm> findAllModules() {
		return moduleRepository.findAll();
	}

	@Override
	public List<Characteristics> findCharacteristicsByModuleId(Integer moduleId) {
		return characteristicsRepository.findCharacteristicsByModuleId(moduleId);
	}
}
