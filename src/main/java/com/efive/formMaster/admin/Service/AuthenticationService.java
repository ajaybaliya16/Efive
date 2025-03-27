package com.efive.formMaster.admin.Service;

import com.efive.formMaster.admin.DTO.LoginUserDto;
import com.efive.formMaster.admin.DTO.RegisterUserDto;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class AuthenticationService {

	private final UserRepo userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	private static final String UPLOAD_DIR = "D:\\formMaster\\images";
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	public AuthenticationService(UserRepo userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}

	public String signup(RegisterUserDto input, MultipartFile file) throws IOException {
		// Check for existing email
		if (userRepository.findByEmail(input.getEmail()).isPresent()) {
			throw new RuntimeException("Email already in use.");
//            return "Email already in use.";
		}

		// Validate and handle file upload
		String[] fileUploadResult = handleFileUpload(file);
		String filePath = fileUploadResult[0];
		String uniqueFileName = fileUploadResult[1];
		String randomPassword = generateRandomPassword(12);
		User user = new User();

		// Set user properties
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setFullName(input.getFirstName() + " " + input.getLastName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(randomPassword));
		user.setContactNo(input.getContactNo());
		user.setGender(input.getGender());
		user.setValidFrom(input.getValidFrom());
		user.setValidTo(input.getValidTo());
		user.setIsActive(Optional.ofNullable(input.getIsActive()).orElse(true));
		user.setRoleId(input.getRoleId());
		user.setImagePath(filePath);
		user.setCreatedBy(input.getCreatedBy());
		user.setUpdatedBy(Optional.ofNullable(input.getUpdatedBy()).orElse((long) 0));
		user.setIsDeleted(Optional.ofNullable(input.getIsDeleted()).orElse(false));
//        user.setImageName(file != null ? uniqueFileName : null);
		user.setImageName(uniqueFileName);

		// Save user and send email
		User savedUser = userRepository.save(user);
		sendPasswordEmail(input.getEmail(), randomPassword);

		return "User registered successfully";
	}

	private String[] handleFileUpload(MultipartFile file) throws IOException {

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

	public User authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new RuntimeException("User not found."));
	}

	private String generateRandomPassword(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	private void sendPasswordEmail(String toEmail, String password) {
		try {
			System.out.println("Sending email to: " + toEmail); // Debug log authenticationManager.authenticate(

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toEmail);
			message.setSubject("Your New Account Password");
			message.setText("Your temporary password is: " + password + "\nPlease change it after logging in.");
			message.setFrom("efivepvt11@gmail.com");

			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send email: " + e.getMessage());
		}
	}
}
