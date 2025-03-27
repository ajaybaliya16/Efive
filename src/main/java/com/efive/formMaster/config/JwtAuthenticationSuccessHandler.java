package com.efive.formMaster.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.efive.formMaster.admin.DTO.LoginResponse;
import com.efive.formMaster.admin.Entity.User;
import com.efive.formMaster.admin.Service.AuthenticationService;
import com.efive.formMaster.admin.Service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority authority : authorities) {
			String role = authority.getAuthority();
			switch (role) {
			case "ROLE_ADMIN":
				response.sendRedirect("/formMaster");
				return;
			case "ROLE_CLIENT":
				response.sendRedirect("/mstFillForm");
				return;
			}
		}

		// Default redirect if no specific role is found
		response.sendRedirect("/index");
	}
}