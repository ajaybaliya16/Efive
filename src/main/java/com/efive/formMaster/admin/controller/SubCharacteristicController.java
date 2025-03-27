package com.efive.formMaster.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.efive.formMaster.admin.Entity.SubCharacteristic;
import com.efive.formMaster.admin.Service.SubCharacteristicService;

import java.util.List;

@RestController
@RequestMapping("/api/characteristics")
public class SubCharacteristicController {

	private final SubCharacteristicService subCharacteristicService;

	@Autowired
	public SubCharacteristicController(SubCharacteristicService subCharacteristicService) {
		this.subCharacteristicService = subCharacteristicService;
	}

	@GetMapping("/{characteristicId}/subcharacteristics")
	public ResponseEntity<List<SubCharacteristic>> getSubCharacteristicsByCharacteristicId(
			@PathVariable("characteristicId") Integer characteristicId) {
		List<SubCharacteristic> subCharacteristics = subCharacteristicService
				.findSubCharacteristicsByCharacteristicId(characteristicId);
		return ResponseEntity.ok(subCharacteristics);
	}
}