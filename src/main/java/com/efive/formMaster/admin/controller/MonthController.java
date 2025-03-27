package com.efive.formMaster.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efive.formMaster.admin.Entity.AnswerType;
import com.efive.formMaster.admin.Entity.Month;
import com.efive.formMaster.admin.Entity.Recurrence;
import com.efive.formMaster.admin.Service.MonthService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MonthController {

	@Autowired
	private MonthService monthService;

	@GetMapping("/months")
	public ResponseEntity<List<Month>> getAllMonths() {
		List<Month> months = monthService.getAllMonths();
		return ResponseEntity.ok(months);
	}

	@GetMapping("/recurrences")
	public ResponseEntity<List<Recurrence>> getAllRecurrences() {
		List<Recurrence> recurrences = monthService.getAllRecurrences();
		return ResponseEntity.ok(recurrences);
	}

	@GetMapping("/answer-types")
	public ResponseEntity<List<AnswerType>> getAllActiveAnswerTypes() {
		List<AnswerType> answerTypes = monthService.getAllActiveAnswerTypes();
		return ResponseEntity.ok(answerTypes);
	}
}