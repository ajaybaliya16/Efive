package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efive.formMaster.admin.Entity.Month;

@Repository
public interface MonthRepository extends JpaRepository<Month, Long> {
}