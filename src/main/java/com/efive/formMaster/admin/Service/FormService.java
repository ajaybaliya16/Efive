package com.efive.formMaster.admin.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import com.efive.formMaster.admin.DTO.FormDTO;
import com.efive.formMaster.admin.Entity.FormEntity;

public interface FormService {
	public String createForm(FormDTO MstFormDTO) throws Exception;

	public String getNextFormId();

	public List<FormDTO> fetchAllFormsWithDetails(Long userId);

	public FormDTO fetchFormById(Long formId);

	public String updateForm(FormDTO formDTO) throws Exception;

	public String softDeleteForm(Long formId, Long loggedInUserId) throws NoSuchElementException;

	
//	List<FormDTO> getAvailableFormsForUser(Long userId);
	public List<FormDTO> getAvailableFormsForUser(Long userId);
	}