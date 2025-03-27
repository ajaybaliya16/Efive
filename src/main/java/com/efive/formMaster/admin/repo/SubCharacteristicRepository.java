package com.efive.formMaster.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efive.formMaster.admin.Entity.SubCharacteristic;

import java.util.List;

public interface SubCharacteristicRepository extends JpaRepository<SubCharacteristic, Integer> {

    @Query(value = "SELECT sc.* FROM sub_characteristics sc " +
                   "WHERE sc.characteristicid = :characteristicId AND sc.active = 1", 
           nativeQuery = true)
    List<SubCharacteristic> findSubCharacteristicsByCharacteristicId(@Param("characteristicId") Integer characteristicId);
}