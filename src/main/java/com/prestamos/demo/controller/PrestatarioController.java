package com.prestamos.demo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	

    @GetMapping("/verificar-correo")
    public Map<String, Boolean> verificarCorreo(@RequestParam String correo) {
        Map<String, Boolean> response = new HashMap<>();
        boolean existe = usurepo.existsByCorreo(correo);
        response.put("existe", existe);
        return response;
    }
	
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
		public String registraUsuarioPT(@ModelAttribute("usuarioPT") Usuarios usu, Principal principal, RedirectAttributes flash) {
		 

			if (usurepo.existsByCorreo(usu.getCorreo())) {
				flash.addFlashAttribute("unsuccess", "Error al registrar: correo '"+ usu.getCorreo() + "' ya existe.");
				return "redirect:/Prestatario/nuevoPrestatario";
			} else {
				
			}
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
		// editar prestataro 
	 @GetMapping("/editarPrestatario/{id}")
	 public String mostrarFormEditar(@PathVariable("id") int id, Model model) {
		 Usuarios usuario = ususerv.obtenerId(id);
		    if (usuario != null) {
		        model.addAttribute("usuarioP", usuario);
		        model.addAttribute("distritos", disService.listaDistritos());
		        return "editarPrestatario";
		    } else {
		        // Manejar el caso en el que no se encuentre el usuario
		        // Por ejemplo, puedes redirigir a una página de error o mostrar un mensaje de error
		        return "error";
		    }
	 }
	 
	 
	 @PostMapping("/editarPrestatario/{id}")
	 public String actualizarUsuario(@PathVariable("id") int id, @ModelAttribute("usuarioP") Usuarios usuario, Model model) {
		    Usuarios usuarioExistente = ususerv.obtenerId(id); // Obtener el usuario existente de la base de datos
		    if (usuarioExistente != null) {
		        // Actualizar los campos del usuario existente con los valores del formulario
		        usuarioExistente.setNombre(usuario.getNombre());
		        usuarioExistente.setApellido(usuario.getApellido());
		        usuarioExistente.setCorreo(usuario.getCorreo());
		        usuarioExistente.setDni(usuario.getDni());
		        usuarioExistente.setTelefono(usuario.getTelefono());
		        //usuarioExistente.setContrasenia(usuario.getContrasenia());
		        usuarioExistente.setNacimiento(usuario.getNacimiento());
		        // Actualizar otras propiedades si es necesario (por ejemplo, idUsuario y distrito)
		        // Guardar el usuario actualizado en la base de datos
		        ususerv.updateUsuario(usuarioExistente);
		        // Redirigir a la página de éxito
		        return "redirect:/Prestatario/editarPrestatario/" + id + "?success";
		    } else {
		        // Manejar el caso en el que no se encuentre el usuario
		        // Por ejemplo, puedes redirigir a una página de error o mostrar un mensaje de error
		        return "error";
		    }
		}

	 
	 @GetMapping("/eliminarPrestatario/{id}")
	 public String eliminarJefePrestamista(@PathVariable("id") int id, RedirectAttributes flash) {
		 
		 Usuarios findUser = usurepo.findById(id);
		 if(findUser!=null) {
			 usurepo.delete(findUser);
			 flash.addFlashAttribute("success", "Se ha eliminado el usuario correctamente");
		 } else {
			 flash.addAttribute("unsuccess", "Error al eliminarlo");
		 }
		 return "redirect:/Prestatario/distritos";
	 }
}
