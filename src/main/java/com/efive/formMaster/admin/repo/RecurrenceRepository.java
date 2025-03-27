package com.efive.formMaster.admin.repo;

import com.efive.formMaster.admin.Entity.Recurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurrenceRepository extends JpaRepository<Recurrence, Integer> {
}