package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efive.formMaster.admin.Entity.OptionEntity;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
}