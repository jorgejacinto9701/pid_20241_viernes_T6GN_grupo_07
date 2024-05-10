package com.prestamos.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.PrestamosRepository;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.PrestamosService;



@Controller
public class PrestamosController {
	

	@Autowired
	private UsuariosRepository usurepo;
	
	@Autowired
	private PrestamosService pres;
	
	@Autowired
	private PrestamosRepository prerepo;
	
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
	
	@GetMapping("/filtrarRegistro")
	public String listaPrestamosPorPrestatario(Model model, Principal principal) {
	    // Obtener el usuario prestamista actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	        // Buscar al usuario prestamista actual en la base de datos
	        Usuarios usuarioPrestamista = usurepo.findByCorreo(currentUser.getUsername());

	        // Verificar si el usuario prestamista existe
	        if (usuarioPrestamista != null) {
	            // Llamar al método del servicio para obtener la lista de préstamos por prestatario
	            List<Prestamos> prestamos = pres.listarPrestamosPrestamista(usuarioPrestamista);
	            // Agregar la lista de préstamos al modelo para que la vista pueda acceder a ellos
	            model.addAttribute("prestamos", prestamos);

	            // Devolver el nombre de la vista que mostrará la lista de préstamos
	            return "FiltrarRegistro";
	        } else {
	            // Manejar el caso en el que el usuario prestamista no se encuentra en la base de datos
	            return "error"; // Por ejemplo, podrías devolver una página de error
	        }
	    } else {
	        // Manejar el caso en el que no haya usuario autenticado
	        // Puedes lanzar una excepción, redirigir a una página de error, etc.
	        return "error"; // Por ejemplo, podrías devolver una página de error
	    }
	}
	
	@PostMapping("/aprobar")
    public String aprobarPrestamo(Integer id) {
        pres.actualizarEstado(id, "APROBADO");
        return "redirect:/filtrarRegistro";
    }
    
    @PostMapping("/desaprobar")
    public String desaprobarPrestamo(Integer id) {
        pres.actualizarEstado(id, "RECHAZADO");
        return "redirect:/filtrarRegistro";
    }

}