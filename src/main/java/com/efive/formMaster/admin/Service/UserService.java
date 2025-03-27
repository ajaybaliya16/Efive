package com.efive.formMaster.admin.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efive.formMaster.admin.DTO.LoginUserDto;
import com.efive.formMaster.admin.DTO.RegisterUserDto;
import com.efive.formMaster.admin.Entity.User;

import io.jsonwebtoken.io.IOException;

public interface UserService {

	public List<RegisterUserDto> findByIsDeletedFalse();

	public String updateUser(Long userId, RegisterUserDto input, MultipartFile file)
			throws IOException, java.io.IOException;

	public String deleteUser(Long userId, Long deletedBy);
	// UserDetails loadUserByUsername(String username) throws
	// UsernameNotFoundException;
//    String authenticateUser(String username, String password);
//    User findByEmail(String email);  

}
