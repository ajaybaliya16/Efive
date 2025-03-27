package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efive.formMaster.admin.Entity.ModuleForm;

public interface ModuleFormRepository extends JpaRepository<ModuleForm, Integer> {
	
	
}