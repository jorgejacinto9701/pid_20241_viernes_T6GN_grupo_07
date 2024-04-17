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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.demo.entity.Distrito;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;
import com.prestamos.demo.service.UsuariosService;

@Controller
@RequestMapping("/JefePrestamista")
public class JefePrestamistaController {

	@Autowired
	private UsuariosRepository usurepo;
	
	@Autowired
	private UsuariosService ususerv;
	
	@Autowired
	private DistritoService disService;
	
	@ModelAttribute("usuarioJP")
	public Usuarios usuario() {
		return new Usuarios();
	}
	
	@GetMapping("/nuevoJefePrestamista")
    public String formJefePrestamista(Model model) {
		model.addAttribute("usuarioJP", new Usuarios());
        return "nuevoJefePrestamista"; 
    }

	 @ResponseBody
	 @GetMapping("/distritos")
	 public List<Distrito> listDistrito(){
		 return disService.listaDistritos();
	  }
	 
	 @PostMapping("/nuevoJefePrestamista")
		public String registraUsuarioJP(@ModelAttribute("usuarioJP") Usuarios usu, Principal principal) {
		 
		 // Obtener el usuario actualmente autenticado
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
		        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		        // Guardar el usuario como Jefe Prestamista
		        ususerv.saveJP(usu, currentUser);
		    } else {
		        // Manejar el caso en el que no haya usuario autenticado
		        // Puedes lanzar una excepción, redirigir a una página de error, etc.
		    }
				
			return "redirect:/JefePrestamista/nuevoJefePrestamista?success";
		}
	 
	 @GetMapping("/listaJefePrestamista")
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
	             model.addAttribute("usuarioJP", jefesPrestamista);

	             // Devolver la vista para mostrar la lista de jefes prestamistas
	             return "listaJefePrestamista";
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
	 
	 @GetMapping("/editarJefePrestamista/{id}")
	 public String mostrarFormEditar(@PathVariable("id") int id, Model model) {
		 Usuarios usuario = ususerv.obtenerId(id);
		    if (usuario != null) {
		        model.addAttribute("usuarioJP", usuario);
		        model.addAttribute("distritos", disService.listaDistritos());
		        return "editarJefePrestamista";
		    } else {
		        // Manejar el caso en el que no se encuentre el usuario
		        // Por ejemplo, puedes redirigir a una página de error o mostrar un mensaje de error
		        return "error";
		    }
	 }
	 
	 @PostMapping("/editarJefePrestamista/{id}")
	 public String actualizarUsuario(Integer id, @ModelAttribute("usuarioJP") Usuarios usuario, Model model) {
		 Usuarios usuarioExiste = ususerv.obtenerId(id);
		 usuarioExiste.setId(id);
		 if (usuario.getNombre() != null) {
		        usuarioExiste.setNombre(usuario.getNombre());
		    }		    
		    if (usuario.getApellido() != null) {
		        usuarioExiste.setApellido(usuario.getApellido());
		    }		    
		    if (usuario.getCorreo() != null) {
		        usuarioExiste.setCorreo(usuario.getCorreo());
		    }		    
		    if (usuario.getDni()!= null) {
		    	usuarioExiste.setDni(usuario.getDni());
		    }
		    if (usuario.getTelefono()!= null) {
		    	usuarioExiste.setTelefono(usuario.getTelefono());
		    }
		    if (usuario.getContrasenia()!= null) {
		    	usuarioExiste.setContrasenia(usuario.getContrasenia());
		    }
		    if(usuario.getNacimiento()!=null) {
		    	usuarioExiste.setNacimiento(usuario.getNacimiento());
		    }
		    if(usuario.getIdUsuario() != null) {
		    	usuarioExiste.setIdUsuario(usuario.getIdUsuario());
		    }
		    if(usuario.getDistrito() != null) {
		    	usuarioExiste.setDistrito(usuario.getDistrito());
		    }
		    
		    ususerv.updateUsuario(usuarioExiste);
		    
		 return "redirect:/JefePrestamista/editarJefePrestamista?success";
	 }
	 
	 //delete nivel coquito gpt
	 @GetMapping("/eliminarJefePrestamista/{id}")
	 public String eliminarJefePrestamista(@PathVariable("id") int id, RedirectAttributes flash) {
		 
		 Usuarios findUser = usurepo.findById(id);
		 if(findUser!=null) {
			 usurepo.delete(findUser);
			 flash.addFlashAttribute("success", "Se ha eliminado le usuario correctamente");
		 } else {
			 flash.addAttribute("unsuccess", "Error al eliminar");
		 }
		 return "redirect:/JefePrestamista/distritos";
	 }
}
