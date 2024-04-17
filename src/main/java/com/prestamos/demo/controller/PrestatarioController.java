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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.demo.entity.Distrito;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;
import com.prestamos.demo.service.UsuariosService;

@Controller
@RequestMapping("/Prestatario")
public class PrestatarioController {
	
	@Autowired
	private UsuariosRepository usurepo;
	
	@Autowired
	private UsuariosService ususerv;
	
	@Autowired
	private DistritoService disService;
	
	@ModelAttribute("usuarioPT")
	public Usuarios usuario() {
		return new Usuarios();
	}
	
	@GetMapping("/nuevoPrestatario")
    public String formJefePrestatario(Model model) {
		model.addAttribute("usuarioPT", new Usuarios());
        return "nuevoPrestatario"; 
    }

	 @ResponseBody
	 @GetMapping("/distritos")
	 public List<Distrito> listDistrito(){
		 return disService.listaDistritos();
	  }
	 
	 @PostMapping("/nuevoPrestatario")
		public String registraUsuarioPT(@ModelAttribute("usuarioPT") Usuarios usu, Principal principal) {
		 
		// Obtener el usuario actualmente autenticado
		 // Obtener el usuario actualmente autenticado
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		        // Guardar el usuario como Jefe Prestamista
		        ususerv.savePT(usu, currentUser);
		    } else {
		        // Manejar el caso en el que no haya usuario autenticado
		        // Puedes lanzar una excepción, redirigir a una página de error, etc.
		    }
				
			return "redirect:/Prestatario/nuevoPrestatario?success";
		}
	 
	 @GetMapping("/listaPrestatario")
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
	             model.addAttribute("usuarioPT", jefesPrestamista);

	             // Devolver la vista para mostrar la lista de jefes prestamistas
	             return "listaPrestatario";
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
	 
	 @GetMapping("/eliminarPrestatario/{id}")
	 public String eliminarJefePrestamista(@PathVariable("id") int id, RedirectAttributes flash) {
		 
		 Usuarios findUser = usurepo.findById(id);
		 if(findUser!=null) {
			 usurepo.delete(findUser);
			 flash.addFlashAttribute("success", "Se ha eliminado le usuario correctamente");
		 } else {
			 flash.addAttribute("unsuccess", "Error al eliminar");
		 }
		 return "redirect:/Prestatario/distritos";
	 }
}
