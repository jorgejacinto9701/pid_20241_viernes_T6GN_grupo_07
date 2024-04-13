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
import com.prestamos.demo.entity.JefePrestamista;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;
import com.prestamos.demo.service.DistritoService;
import com.prestamos.demo.service.JefePrestamistaService;
import com.prestamos.demo.service.UsuariosService;

@Controller
@RequestMapping("/registro")
@PreAuthorize("denyAll()")
public class UsuariosController {

    @Autowired
    private UsuariosService service;
    
    @Autowired
    private DistritoService serviceDis;
    

    @Autowired
    private JefePrestamistaService jefePrestamistaService;

    
    @Autowired
    private UsuariosRepository usure;

    
    @ModelAttribute("usuarios")
    public Usuarios newUser() {
        return new Usuarios();
    }
    
    @ResponseBody
    @GetMapping("/distritos")
    public List<Distrito> listDistrito(){
    	return serviceDis.listaDistritos();
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ROL_INVERSIONISTA')")
    public String formUser(Model model) {
    	
    	//List<Distrito> listDistrito = serviceDis.listaDistritos();
    	//model.addAttribute("distrito", listDistrito);
        return "registro";
    }
    
    @PostMapping("/jefePrestamista") // Cambia la anotación de GetMapping a PostMapping
    @PreAuthorize("hasAuthority('ROL_INVERSIONISTA')")
    public String registrarJP(@ModelAttribute("usuarios") JefePrestamista jefePrestamista) {
        // Obteniendo el correo del usuario actual (esto puede variar dependiendo de cómo manejes la autenticación)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        
        // Llamando al método para registrar al jefe prestamista
        jefePrestamistaService.newJP(jefePrestamista, correo);
        
        return "redirect:/registro?exito"; // Redirige a donde sea necesario después de registrar
    }
    
    /*@PostMapping
    public String registrarUsuario(@ModelAttribute("usuarios") Usuarios registro) {
        service.newUsuarioJP(registro);
        return "redirect:/registro?exito";
    }*/
    
    
    @GetMapping("/insertar/prestamista")
    @PreAuthorize("hasAuthority('ROL_JEFE_PRESTAMISTA')")
    public String someMethod(Model model) {
    	
    	
    	
    	return "insertarPrestamista";
    }
    
    
    
    
    
    
    
    
    
 // --------------------crud usuarios (prestatarios)-----------------
    
   //listar usuario
    
    @GetMapping("/lstusuario")
    public String listadousuarios(Model model) {
    	model.addAttribute("lstusuarios",usure.findAll());
    	return "listadousuario";	
    }
    
    
    //grabar usuarios
    
    
    @GetMapping("/insertar/usuario")
  //  @PreAuthorize("hasAuthority('ROL_PRESTAMISTA')")
    public String cargarpag(Model model) {
    	Usuarios usu = new Usuarios();
    	model.addAttribute("usuario",usu);
    	return "insertarUsuario";
    }
    
    
    @PostMapping("/insertar/usuario")
   // @PreAuthorize("hasAuthority('ROL_PRESTAMISTA')")
    public String insertarusuario(@ModelAttribute Usuarios usu,RedirectAttributes attribute) {
    
    if(usure.save(usu) != null) {
		attribute.addFlashAttribute("success", "Registrado con éxito!");
	}else {
		attribute.addFlashAttribute("unsuccess", "Error al Registrar!");
	}
	return "redirect:/lstusuario";
}

    
    
    
    
    
    
    
    
}
