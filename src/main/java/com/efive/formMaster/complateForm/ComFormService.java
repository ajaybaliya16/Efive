package com.efive.formMaster.complateForm;

import java.util.List;

public interface ComFormService {
	List<CompletedFormDTO> getCompletedForms(Long userId);

	FormPreviewDTO getFormPreview(Long responseId, Long userId);
}