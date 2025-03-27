package com.efive.formMaster.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.efive.formMaster.admin.Entity.Characteristics;
import com.efive.formMaster.admin.Entity.ModuleForm;
import com.efive.formMaster.admin.Service.ModuleFormService;

@RestController
@RequestMapping("/api/modules")
public class DropdownData {

	private final ModuleFormService moduleService;

	@Autowired
	public DropdownData(ModuleFormService moduleService) {
		this.moduleService = moduleService;
	}

	@GetMapping
	public ResponseEntity<List<ModuleForm>> getAllModules() {
		List<ModuleForm> modules = null;
		try {
			modules = moduleService.findAllModules();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(modules);
	}

	@GetMapping("/{moduleId}/characteristics")
	public ResponseEntity<List<Characteristics>> getCharacteristicsByModuleId(
			@PathVariable("moduleId") Integer moduleId) {
		List<Characteristics> characteristics = moduleService.findCharacteristicsByModuleId(moduleId);
		return ResponseEntity.ok(characteristics);
	}

}
