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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.demo.entity.Distrito;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;
import com.prestamos.demo.service.UsuariosService;

@Controller
@RequestMapping("/Prestamista")
//@RestController("/prestamista")
public class PrestamistaController {
	
	@Autowired
	private UsuariosRepository usurepo;
	
	@Autowired
	private UsuariosService ususerv;
	
	@Autowired
	private DistritoService disService;
	
	@ModelAttribute("usuarioP")
	public Usuarios usuario() {
		return new Usuarios();
	}
	
	@GetMapping("/nuevoPrestamista")
    public String formJefePrestamista(Model model) {
		model.addAttribute("usuarioP", new Usuarios());
        return "nuevoPrestamista"; 
    }

	 @ResponseBody
	 @GetMapping("/distritos")
	 public List<Distrito> listDistrito(){
		 return disService.listaDistritos();
	  }
	 
	 @PostMapping("/nuevoPrestamista")
		public String registraUsuarioP(@ModelAttribute("usuarioP") Usuarios usu, Principal principal) {
		 
		// Obtener el usuario actualmente autenticado
		 // Obtener el usuario actualmente autenticado
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		        // Guardar el usuario como Jefe Prestamista
		        ususerv.saveP(usu, currentUser);
		    } else {
		        // Manejar el caso en el que no haya usuario autenticado
		        // Puedes lanzar una excepción, redirigir a una página de error, etc.
		    }
				
			return "redirect:/Prestamista/nuevoPrestamista?success";
		}
	 @GetMapping("/listaPrestamista")
	 public String listarJefesPrestamista(Model model, Principal principal) {
	     // Obtener el usuario actualmente autenticado
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	         UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	         // Buscar al usuario inversionista actual en la base de datos
	         Usuarios usuarioInversionista = usurepo.findByCorreo(currentUser.getUsername());

	         // Verificar si el usuario inversionista existe
	         if (usuarioInversionista != null) {
	             // Obtener la lista de jefes prestamistas asociados al usuario inversionista
	             List<Usuarios> jefesPrestamista = ususerv.listJefePrestamista(usuarioInversionista);

	             // Agregar la lista de jefes prestamistas al modelo
	             model.addAttribute("usuarioP", jefesPrestamista);

	             // Devolver la vista para mostrar la lista de jefes prestamistas
	             return "listaPrestamista";
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
	 
	 @GetMapping("/eliminarPrestamista/{id}")
	 public String eliminarJefePrestamista(@PathVariable("id") int id, RedirectAttributes flash) {
		 
		 Usuarios findUser = usurepo.findById(id);
		 if(findUser!=null) {
			 usurepo.delete(findUser);
			 flash.addFlashAttribute("success", "Se ha eliminado le usuario correctamente");
		 } else {
			 flash.addAttribute("unsuccess", "Error al eliminar");
		 }
		 return "redirect:/Prestamista/distritos";
	 }

	
}
