package com.efive.formMaster.admin.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efive.formMaster.admin.Entity.AnswerType;
import com.efive.formMaster.admin.Entity.Month;
import com.efive.formMaster.admin.Entity.Recurrence;
import com.efive.formMaster.admin.Service.MonthService;
import com.efive.formMaster.admin.repo.AnswerTypeRepository;
import com.efive.formMaster.admin.repo.MonthRepository;
import com.efive.formMaster.admin.repo.RecurrenceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthServiceImpl implements MonthService {

	@Autowired
	private MonthRepository monthRepository;

	@Autowired
	private RecurrenceRepository recurrenceRepository;
	@Autowired
	private AnswerTypeRepository answerTypeRepository;

	@Override
	public List<Month> getAllMonths() {
		return monthRepository.findAll();
	}

	@Override
	public List<Recurrence> getAllRecurrences() {
		return recurrenceRepository.findAll();
	}

	@Override
	public List<AnswerType> getAllActiveAnswerTypes() {
		return answerTypeRepository.findAll();

	}
}