package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efive.formMaster.admin.Entity.Characteristics;

import java.util.List;

public interface CharacteristicsRepository extends JpaRepository<Characteristics, Integer> {

	@Query(value = "SELECT c.* FROM characteristics c " +
            "JOIN mst_module_characteristics_mapping mc ON c.characteristicid = mc.characteristicid " +
            "WHERE mc.moduleid = :moduleId AND c.active = 1", 
    nativeQuery = true)
    List<Characteristics> findCharacteristicsByModuleId(@Param("moduleId") Integer moduleId);
}