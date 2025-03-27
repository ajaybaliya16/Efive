package com.efive.formMaster.admin.Service;

import java.util.List;

import com.efive.formMaster.admin.Entity.SubCharacteristic;

public interface SubCharacteristicService {
	List<SubCharacteristic> findSubCharacteristicsByCharacteristicId(Integer characteristicId);
}