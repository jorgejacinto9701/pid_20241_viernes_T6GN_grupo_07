package com.prestamos.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login2")
    public String login() {
        return "login"; // Esto debe coincidir con el nombre de tu archivo HTML del formulario de inicio de sesión
    }
}