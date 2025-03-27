package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efive.formMaster.admin.Entity.MstForm;

public interface MstFormRepository extends JpaRepository<MstForm, Long> {
}