package com.prestamos.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.PrestamosService;

@Controller
public class PrestamosController {
	
	@Autowired
	private UsuariosRepository usurepo;
	
	@Autowired
	private PrestamosService pres;
	
	@GetMapping("/solicitarPrestamo")
	public String tablaPrestamo() {
		return "solicitarPrestamo";
	}
	
	@GetMapping("/nuevoPrestamo")
	public String calculadora() {
		return "CalculadoraMonto";
	}
	
	@PostMapping("/nuevoPrestamo")
	public String nuevoPrestamo(@ModelAttribute("prestamo") Prestamos prestamo, Principal principal) {
		
		// Obtener el usuario actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
	        // Guardar el usuario como Jefe Prestamista
	        pres.save(prestamo, currentUser);
	    } else {
	        // Manejar el caso en el que no haya usuario autenticado
	        // Puedes lanzar una excepción, redirigir a una página de error, etc.
	    }
		
		return "redirect:/nuevoPrestamo?success";
	}
	
	@GetMapping("/listaPrestamos")
	public String listarPrestamos(Model model, Principal principal) {
		 // Obtener el usuario actualmente autenticado
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	         UserDetails currentUser = (UserDetails) authentication.getPrincipal();
	         
	       //Buscar al usuario prestatario actual en la base de datos
	 	    Usuarios usuarioPrestatario = usurepo.findByCorreo(currentUser.getUsername());

	         // Verificar si el usuario existe
	         if (usuarioPrestatario != null) {
	             // Obtener la lista de prestamos relacionadas al usuario
	             List<Prestamos> prestamos = pres.listarPrestamos(usuarioPrestatario);

	             // Agregar la lista de jefes prestamistas al modelo
	             model.addAttribute("prestamo", prestamos);

	             // Devolver la vista para mostrar la lista de jefes prestamistas
	             return "listaPrestamos";
	         } else {
	             // Manejar el caso en el que el usuario inversionista no se encuentra en la base de datos
	             return "error"; // Por ejemplo, podrías devolver una página de error
	         }
	     } else {
	         // Manejar el caso en el que no haya usuario autenticado
	         // Puedes lanzar una excepción, redirigir a una página de error, etc.
	         return "error"; // Por ejemplo, podrías devolver una página de error
	     }
	}
	

}
