package com.efive.formMaster.admin.controller;

import com.efive.formMaster.admin.DTO.FormDTO;
import com.efive.formMaster.admin.Entity.FormEntity;
import com.efive.formMaster.admin.Entity.ModuleForm;
import com.efive.formMaster.admin.Service.FormService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forms")
public class FormController {

	private final FormService formService;

	@Autowired
	public FormController(FormService formService) {
		this.formService = formService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createForm(@RequestBody FormDTO formDTO, HttpSession session) {

		Long loggedInUserId = (Long) session.getAttribute("userId");

		if (loggedInUserId == null) {
			return ResponseEntity.badRequest().body("User not logged in.");
		}

		// Set the updatedBy field with the logged-in user ID
		formDTO.setCreatedBy(loggedInUserId);
		if (formDTO == null) {
			return new ResponseEntity<>("Request body is null", HttpStatus.BAD_REQUEST);
		}
		if (formDTO.getFormTitle() == null) {
			return new ResponseEntity<>("Form title is null", HttpStatus.BAD_REQUEST);
		}

		try {
			String result = formService.createForm(formDTO);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating the form: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/next-id")
	public ResponseEntity<String> getNextFormId() {
		try {
			String nextId = formService.getNextFormId();
			return new ResponseEntity<>(nextId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error fetching next form ID: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all-details")
	public ResponseEntity<List<FormDTO>> fetchAllFormsWithDetails(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401 if not logged in
		}
		try {
			List<FormDTO> forms = formService.fetchAllFormsWithDetails(userId); // Pass userId to service
			return new ResponseEntity<>(forms, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<FormDTO> getFormById(@PathVariable("id") Long id) {
		try {
			FormDTO formDTO = formService.fetchFormById(id);
			return ResponseEntity.ok(formDTO);
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateForm(@RequestBody FormDTO formDTO, HttpSession session) {
		try {
			Long loggedInUserId = (Long) session.getAttribute("userId");

			if (loggedInUserId == null) {
				return ResponseEntity.badRequest().body("User not logged in.");
			}

			// Set the updatedBy field with the logged-in user ID
			formDTO.setUpdatedBy(loggedInUserId);

			String result = formService.updateForm(formDTO);
			return ResponseEntity.ok(result);

		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to update form: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{formId}")
	public ResponseEntity<String> softDeleteForm(@PathVariable("formId") Long formId, HttpSession session) {
		try {
			Long loggedInUserId = (Long) session.getAttribute("userId");
//            Long loggedInUserId = getCurrentUserId(); 

			if (loggedInUserId == null) {
				return ResponseEntity.badRequest().body("User not logged in.");
			}

			String result = formService.softDeleteForm(formId, loggedInUserId);
			return ResponseEntity.ok(result);

		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to soft delete form: " + e.getMessage());
		}
	}

	@GetMapping("/available")
	public ResponseEntity<List<FormDTO>> getAvailableForms(HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		List<FormDTO> availableForms = formService.getAvailableFormsForUser(userId);
		return ResponseEntity.ok(availableForms);
	}

}