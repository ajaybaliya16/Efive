package com.efive.formMaster.admin.controller;

import com.efive.formMaster.admin.DTO.LoginUserDto;
import com.efive.formMaster.admin.DTO.RegisterUserDto;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.Service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/api/users/all")
	public ResponseEntity<List<RegisterUserDto>> getAllUsers() {
		try {
			List<RegisterUserDto> users = userService.findByIsDeletedFalse();
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/image/{imageName}")
	public ResponseEntity<Resource> serveImage(@PathVariable("imageName") String imageName) {
		try {
			Path imagePath = Paths.get("D:\\formMaster\\images").resolve(imageName);
			if (!Files.exists(imagePath)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			Resource resource = new UrlResource(imagePath.toUri());
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; imageName=\"" + imageName + "\"")
						.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath)).body(resource);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping("/update/{userId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateUser(@PathVariable("userId") Long userId,
			@ModelAttribute RegisterUserDto userDto, @RequestParam(value = "file", required = false) MultipartFile file, // Optional
																															// file
																															// upload
			HttpSession session) throws IOException {

		// Get the user ID from the session, with null check
		Long updatedBy = (Long) session.getAttribute("userId");
		if (updatedBy == null) {
			updatedBy = 0L; // Default to 0 for anonymous or unauthenticated updates
		}

		// Set the updatedBy field on the DTO instance
		userDto.setUpdatedBy(updatedBy);

		try {
			// Call the service to handle user update
			String response = userService.updateUser(userId, userDto, file);

			// Return a success response as JSON
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", response);
			return ResponseEntity.ok(responseBody);
		} catch (Exception e) {
			e.printStackTrace();

			// Return an error response as JSON
			Map<String, String> errorBody = new HashMap<>();
			errorBody.put("error", "User update failed due to an unexpected error.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId, HttpSession session) {
		try {
			Long loggedInUserId = (Long) session.getAttribute("userId");

			if (loggedInUserId == null) {
				return ResponseEntity.badRequest().body("User not logged in.");
			}

			String response = userService.deleteUser(userId, loggedInUserId);
			return ResponseEntity.ok(response);

		} catch (RuntimeException e) {
			return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
		}
	}
}