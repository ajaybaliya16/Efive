package com.efive.formMaster.admin.repo;

import com.efive.formMaster.admin.Entity.AnswerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerTypeRepository extends JpaRepository<AnswerType, Integer> {
}