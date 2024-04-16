package com.prestamos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model) {
	return "login";
	}
	
	@GetMapping("/home")
	public String home() {	
		return "index";
	}
}