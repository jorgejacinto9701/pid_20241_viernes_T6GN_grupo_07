package com.prestamo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prestamos.entity.Usuarios;
import com.prestamos.service.UsuariosService;

@Controller
@RequestMapping("/registro")
public class UsuariosController {

    @Autowired
    private UsuariosService service;
    
    @ModelAttribute("usuarios")
    public Usuarios newUser() {
        return new Usuarios();
    }
    
    @GetMapping
    public String formUser() {
        return "registro";
    }
    
    @PostMapping
    public String registrarUsuario(@ModelAttribute("usuarios") Usuarios registro) {
        service.insertUsuario(registro);
        return "redirect:/registro?exito";
    }
}
