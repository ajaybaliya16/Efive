package com.efive.formMaster.AnswerForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/forms/ans")
public class AsnFormController {

	@Autowired
	private FormSubmissionService formSubmissionService;

	@PostMapping("/submit")
	public ResponseEntity<String> submitForm(@RequestBody FormSubmissionRequest formSubmissionRequest,
			HttpSession session) {
		// Retrieve logged-in user ID from session
		Long loggedInUserId = (Long) session.getAttribute("userId");
		System.err.print("user logged :" + loggedInUserId);
		// Validate if user is logged in
		if (loggedInUserId == null) {
			return ResponseEntity.badRequest().body("User not logged in.");
		}

		// Validate request body
		if (formSubmissionRequest == null) {
			return new ResponseEntity<>("Request body is null", HttpStatus.BAD_REQUEST);
		}

		formSubmissionRequest.setUserId(loggedInUserId);
		// Validate required fields in the request
		if (formSubmissionRequest.getFormId() == null) {
			return new ResponseEntity<>("Form ID is null", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = formSubmissionService.submitForm(formSubmissionRequest);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while submitting the form: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}