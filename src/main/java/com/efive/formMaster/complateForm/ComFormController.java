package com.efive.formMaster.complateForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
public class ComFormController {

	@Autowired
	private ComFormService comFormService;

	@GetMapping("/completed")
	public ResponseEntity<List<CompletedFormDTO>> getCompletedForms(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(401).body(null); // Unauthorized if no userId in session
		}
		List<CompletedFormDTO> completedForms = comFormService.getCompletedForms(userId);
		return ResponseEntity.ok(completedForms);
	}

	@GetMapping("/preview/{responseId}")
	public ResponseEntity<FormPreviewDTO> getFormPreview(@PathVariable("responseId") Long responseId,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(401).body(null); // Unauthorized if no userId in session
		}
		FormPreviewDTO preview = comFormService.getFormPreview(responseId, userId);
		return ResponseEntity.ok(preview);
	}
}