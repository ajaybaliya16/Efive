package com.efive.formMaster.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        
        String UserFail = "Invalid User Name and Password.";
        session.setAttribute("userfail", UserFail);
        System.err.println("Authentication failed: ");
        response.sendRedirect("/index");

    }
}
//
//package com.efive.formMaster.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component // This registers the class as a Spring bean
//public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFailureHandler.class);
//
//    @Override
//    public void onAuthenticationFailure(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException exception
//    ) throws IOException, ServletException {
//        logger.warn("Authentication failed: {}", exception.getMessage());
//        response.sendRedirect("/index"); // Redirect to /index
//    }
//}