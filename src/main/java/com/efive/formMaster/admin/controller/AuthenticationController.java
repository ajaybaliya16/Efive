package com.efive.formMaster.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efive.formMaster.admin.DTO.LoginResponse;
import com.efive.formMaster.admin.DTO.LoginUserDto;
import com.efive.formMaster.admin.DTO.RegisterUserDto;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.Service.AuthenticationService;
import com.efive.formMaster.admin.Service.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtService jwtService;
	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<Map<String, String>> register(
	        @ModelAttribute RegisterUserDto registerUserDto,
	        @RequestParam(value = "file", required = false) MultipartFile file,
	        HttpSession session) throws IOException {

	    // Get the user ID from the session, with null check
	    Long existingUserId = (Long) session.getAttribute("userId");
	    if (existingUserId == null) {
	        existingUserId = 0L; // Default to 0 or handle as needed
	    }

	    // Set the createdBy field on the DTO instance
	    registerUserDto.setCreatedBy(existingUserId);

	    try {
	        // Call the service to handle signup
	        String response = authenticationService.signup(registerUserDto, file);

	        // Return a success response as JSON
	        Map<String, String> responseBody = new HashMap<>();
	        responseBody.put("message", response); // Use the service's message
	        return ResponseEntity.ok(responseBody);
	    } catch (Exception e) {
	        e.printStackTrace();

	        // Return an error response as JSON
	        Map<String, String> errorBody = new HashMap<>();
	        // Check if the exception message indicates an email conflict
	        String errorMessage = (e.getMessage() != null && e.getMessage().contains("Email")) 
	                ? "Email already registered." 
	                : "An error occurred while  registering the user.";
	        errorBody.put("error", errorMessage);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody); // Use 400 instead of 500 for client error
	    }
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto,
			HttpServletRequest request) {

		User authenticatedUser = authenticationService.authenticate(loginUserDto);
		System.out.println(loginUserDto);
		String jwtToken = jwtService.generateToken(authenticatedUser);

		HttpSession session = request.getSession();
		session.setAttribute("jwtToken", "Bearer " + jwtToken);
		session.setAttribute("userName", authenticatedUser.getFirstName());
		session.setAttribute("userId", authenticatedUser.getId());
//        String user = (String) session.getAttribute("userId");
//        System.err.print(user);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		loginResponse.setRoleId(authenticatedUser.getRoleId());
		loginResponse.setFullName(authenticatedUser.getFullName());

//        LoginUserDto.settoken(jwtToken);

		return ResponseEntity.ok(loginResponse);
	}
	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
		try {
			// Get the current session
			HttpSession session = request.getSession(false); // false means don't create new session if none exists

			// If session exists, invalidate it and clear attributes
			if (session != null) {
				session.removeAttribute("jwtToken");
				session.removeAttribute("userName");
				session.removeAttribute("userId");
				session.invalidate();
			}

			// Prepare success response
			Map<String, String> responseBody = new HashMap<>();
			responseBody.put("message", "Successfully logged out");
			return ResponseEntity.ok(responseBody);

		} catch (Exception e) {
			e.printStackTrace();

			// Prepare error response
			Map<String, String> errorBody = new HashMap<>();
			errorBody.put("error", "Logout failed due to an unexpected error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
		}
	}
}