package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.efive.formMaster.admin.Entity.FormSubmissionEntity;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmissionEntity, Long> {

}
