package com.prestamos.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.demo.entity.Distrito;

import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;

import com.prestamos.demo.service.UsuariosService;
@Controller
@RequestMapping("/registro")
@PreAuthorize("denyAll()")
public class UsuariosController {

	@Autowired
	private UsuariosService ususerv;
	
	
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