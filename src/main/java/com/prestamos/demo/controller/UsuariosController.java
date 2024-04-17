package com.prestamos.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.service.UsuariosService;
=======
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.demo.entity.Distrito;

import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;
>>>>>>> 7b433c0e96f431d90f4a7d270f3d78d2d8e8810d

import com.prestamos.demo.service.UsuariosService;
@Controller
@RequestMapping("/registro")
@PreAuthorize("denyAll()")
public class UsuariosController {

	@Autowired
	private UsuariosService ususerv;
	
<<<<<<< HEAD
	@ModelAttribute("usuario")
	public Usuarios usuario() {
		return new Usuarios();
	}
	
	@GetMapping
	public String showFormRegistro(Model model) {
		model.addAttribute("usuario", new Usuarios());
		return "registro";
	}
	
	@PostMapping
	public String registraUsuario(@ModelAttribute("usuario") Usuarios usu) {	
		ususerv.save(usu);		
		return "redirect:/registro?success";
	}

    
    
    
    
    
    
    
    
}
=======
	
	@ModelAttribute("usuario")
	public Usuarios usuario() {
		return new Usuarios();
	}
	
	@GetMapping   
	public String showFormRegistro(Model model) {
		model.addAttribute("usuario", new Usuarios());
		return "registro";
	}
	
	@PostMapping
	public String registraUsuario(@ModelAttribute("usuario") Usuarios usu) {	
		ususerv.save(usu);		
		return "redirect:/registro?success";
	}}
>>>>>>> 7b433c0e96f431d90f4a7d270f3d78d2d8e8810d
