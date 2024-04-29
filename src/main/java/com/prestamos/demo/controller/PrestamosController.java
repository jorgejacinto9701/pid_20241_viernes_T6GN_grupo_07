package com.prestamos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrestamosController {
	
	@GetMapping("/solicitarPrestamo")
	public String tablaPrestamo() {
		return "solicitarPrestamo";
	}

}
