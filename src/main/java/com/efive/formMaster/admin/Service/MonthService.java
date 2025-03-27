package com.efive.formMaster.admin.Service;

import java.util.List;

import com.efive.formMaster.admin.Entity.AnswerType;
import com.efive.formMaster.admin.Entity.Month;
import com.efive.formMaster.admin.Entity.Recurrence;

public interface MonthService {
	List<Month> getAllMonths();

	List<Recurrence> getAllRecurrences();

	List<AnswerType> getAllActiveAnswerTypes();
}