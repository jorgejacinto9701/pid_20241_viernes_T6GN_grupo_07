package com.prestamos.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;

@Controller
public class LoginController {

    @GetMapping("/loggin")
    public String login(org.springframework.ui.Model model) {
    	  model.addAttribute("correo","");
    	  model.addAttribute("contrasenia","");
          
          
        return "loggin"; // Esto debe coincidir con el nombre de tu archivo HTML del formulario de inicio de sesión
    }
}