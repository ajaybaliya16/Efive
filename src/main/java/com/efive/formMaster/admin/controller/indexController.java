package com.efive.formMaster.admin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

	@GetMapping("/index")
	public String indexPage() {
		return "index"; // Directly return the index page
	}

	@GetMapping("/formMaster")
	public String foem() {
		return "master_form"; // Directly return the index page
	}

	@GetMapping("/mstUser")
	public String masterUser() {
		return "master_users"; // Directly return the index page
	}

	@GetMapping("/mstFillForm")
	public String fillform() {
		return "fill_forms"; // Directly return the index page
	}

	@GetMapping("/mstComplateForm")
	public String ComplateForm() {
		return "completed_forms";
	}

	@GetMapping("/mstProfile")
	public String profile() {
		return "profile";
	}
}
