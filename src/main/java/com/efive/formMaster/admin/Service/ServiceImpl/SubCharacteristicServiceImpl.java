package com.efive.formMaster.admin.Service.ServiceImpl;

import com.efive.formMaster.admin.Entity.SubCharacteristic;
import com.efive.formMaster.admin.Service.SubCharacteristicService;
import com.efive.formMaster.admin.repo.SubCharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCharacteristicServiceImpl implements SubCharacteristicService {

	private final SubCharacteristicRepository subCharacteristicRepository;

	@Autowired
	public SubCharacteristicServiceImpl(SubCharacteristicRepository subCharacteristicRepository) {
		this.subCharacteristicRepository = subCharacteristicRepository;
	}

	@Override
	public List<SubCharacteristic> findSubCharacteristicsByCharacteristicId(Integer characteristicId) {
		return subCharacteristicRepository.findSubCharacteristicsByCharacteristicId(characteristicId);
	}
}