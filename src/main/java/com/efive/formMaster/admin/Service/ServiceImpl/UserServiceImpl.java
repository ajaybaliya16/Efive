package com.efive.formMaster.admin.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efive.formMaster.admin.DTO.LoginUserDto;
import com.efive.formMaster.admin.DTO.RegisterUserDto;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.Service.UserService;
import com.efive.formMaster.admin.repo.UserRepo;

import io.jsonwebtoken.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	private static final String UPLOAD_DIR = "D:\\formMaster\\images";
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

	@Override
	public List<RegisterUserDto> findByIsDeletedFalse() {

		List<RegisterUserDto> userList = new ArrayList<>();

		try {
			List<User> results = userRepo.findByIsDeletedFalse();

			for (User user : results) {
				RegisterUserDto userDto = new RegisterUserDto(user.getFirstName(), user.getLastName(), user.getEmail(),
						user.getContactNo(), user.getGender(), user.getValidFrom(), user.getValidTo(), user.getRoleId(),
						user.getIsActive(), user.getImagePath(), user.getImageName(), user.getId()

				);
				userList.add(userDto);
			}
		} catch (Exception e) {
			System.err.println("Error retrieving users: " + e.getMessage());
		}

		return userList;
	}

	@Override
	public String updateUser(Long userId, RegisterUserDto input, MultipartFile file)
			throws IOException, java.io.IOException {
		// Check if user exists
		Optional<User> optionalUser = userRepo.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new RuntimeException("User not found.");
		}

		User user = optionalUser.get();

		// Check for email uniqueness (excluding the current user)
		Optional<User> existingUserWithEmail = userRepo.findByEmail(input.getEmail());
		if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
			throw new RuntimeException("Email already in use by another user.");
		}

		// Handle file upload if provided
		String filePath = user.getImagePath(); // Retain existing if no new file
		String uniqueFileName = user.getImageName();
		if (file != null && !file.isEmpty()) {
			String[] fileUploadResult = handleFileUpload(file);
			filePath = fileUploadResult[0];
			uniqueFileName = fileUploadResult[1];
		}

		// Update user properties
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setFullName(input.getFirstName() + " " + input.getLastName());
		user.setEmail(input.getEmail());
		user.setContactNo(input.getContactNo());
		user.setGender(input.getGender());
		user.setValidFrom(input.getValidFrom());
		user.setValidTo(input.getValidTo());
		user.setIsActive(Optional.ofNullable(input.getIsActive()).orElse(true));
		user.setRoleId(input.getRoleId());
		user.setImagePath(filePath);
		user.setImageName(uniqueFileName);
		user.setUpdatedBy(Optional.ofNullable(input.getUpdatedBy()).orElse(0L));
		user.setUpdatedAt(LocalDateTime.now());
		// Note: Password and isDeleted are not updated here; add if needed

		// Save updated user
		userRepo.save(user);

		return "User updated successfully";
	}

	// Reuse the existing handleFileUpload method
	private String[] handleFileUpload(MultipartFile file) throws IOException, java.io.IOException {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("No file uploaded");
		}

		if (file.getSize() > MAX_FILE_SIZE) {
			throw new IllegalArgumentException("File size exceeds maximum limit of 5MB");
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || originalFilename.isEmpty()) {
			throw new IllegalArgumentException("Invalid file name");
		}

		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
			throw new IllegalArgumentException("Invalid file type. Allowed types: " + ALLOWED_EXTENSIONS);
		}

		Path uploadPath = Paths.get(UPLOAD_DIR);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
		Path filePath = uploadPath.resolve(uniqueFileName);

		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Failed to save file: " + e.getMessage(), e);
		}

		return new String[] { filePath.toString(), uniqueFileName };
	}

	@Override
//    @Transactional
	public String deleteUser(Long userId, Long deletedBy) throws RuntimeException {
		// Find the user by ID
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

		// Prevent self-deletion (optional, adjust as needed)
		if (userId.equals(deletedBy)) {
			throw new RuntimeException("You cannot delete your own account.");
		}

		// Soft deletion: Mark user as deleted
		user.setIsDeleted(true); // Assuming a boolean field `isDeleted`
		user.setUpdatedBy(deletedBy); // Track who deleted the user
		user.setUpdatedAt(LocalDateTime.now()); // Optional: timestamp of deletion

		// Save the updated user entity
		userRepo.save(user);

		// Alternative: Hard deletion (uncomment if preferred)
		// userRepository.delete(user);

		return "User with ID " + userId + " deleted successfully";
	}

}

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authmanager;

//    @Autowired
//    private static AuthenticationManager authenticationManager; 
//
//    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public static String verify(userDTO userDTO) {
//    	
//    	
//    	 Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
//    	 if (authentication.isAuthenticated()) {
//             return JWTService.generateToken(userDTO.getUsername());
//         } else {
//             return "fail";
//         }
//    }
//}

//@Override
//public User findByEmail(String email) {
//  return userRepo.findByEmail(email).orElse(null);
//}

//// Load user by email (required by Spring Security's UserDetailsService)
//@Override
//public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//  User user = userRepo.findByEmail(email)
//          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//  // Add dynamic role management if needed
//  return org.springframework.security.core.userdetails.User.builder()
//          .username(user.getEmail())
//          .password(user.getPassword())
//          .roles("USER") // Replace with dynamic role if needed
//          .build();
//}

//// Authenticate user
//public String authenticateUser(String email, String password) {
//  try {
//      // Create authentication token
//      org.springframework.security.core.Authentication authentication = 
//          authmanager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//      return "Authentication successful";
//  } catch (Exception e) {
//      e.printStackTrace();
//      return "Authentication failed: " + e.getMessage();
//  }
//}
//