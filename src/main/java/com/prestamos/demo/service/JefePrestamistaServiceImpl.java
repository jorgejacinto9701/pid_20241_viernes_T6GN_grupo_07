package com.prestamos.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.JefePrestamista;
import com.prestamos.demo.entity.Rol;
import com.prestamos.demo.entity.RolName;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.JefePrestamistaRepository;
import com.prestamos.demo.repository.UsuariosRepository;

@Service
public class JefePrestamistaServiceImpl implements JefePrestamistaService{

	@Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    @Autowired
    private UsuariosRepository usuariosrepo; //

	@Override
	public JefePrestamista newJP(JefePrestamista obj, String correo) {
		 // Obtener el usuario inversionista
	    Usuarios inversionista = usuariosrepo.findByCorreoAndRolesNombreRol(correo, "ROL_INVERSIONISTA")
	    			.orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

	    // Asignar el ID del usuario inversionista al campo id_usuario del jefe prestamista
	    obj.setUsuario(inversionista);
	    
	    // Guardar el jefe prestamista en la tabla jefe_prestamista
	    JefePrestamista nuevoJefePrestamista = jefePrestamistaRepository.save(obj);
	    
	    // Crear un nuevo usuario con el rol de "Jefe Prestamista"
	    Usuarios nuevoUsuarioJefePrestamista = new Usuarios();
	    nuevoUsuarioJefePrestamista.setNombre(obj.getNombre_jp());
	    nuevoUsuarioJefePrestamista.setApellido(obj.getApellido_jp());
	    nuevoUsuarioJefePrestamista.setCorreo(obj.getCorreo_jp());
	    nuevoUsuarioJefePrestamista.setDni(obj.getDni_jp());
	    nuevoUsuarioJefePrestamista.setTelefono(obj.getTelefono_jp());
	    nuevoUsuarioJefePrestamista.setContrasenia(obj.getContrasenia_jp());
	    nuevoUsuarioJefePrestamista.setNacimiento(obj.getNacimiento_jp());
	 // Crear una lista que contenga el rol de "Jefe Prestamista"
	    List<Rol> rolesJefePrestamista = new ArrayList<>();
	    Rol rolJefePrestamista = new Rol();
	    rolJefePrestamista.setNombreRol(RolName.ROL_JEFE_PRESTAMISTA);
	    rolesJefePrestamista.add(rolJefePrestamista);

	    // Establecer los roles del nuevo usuario
	    nuevoUsuarioJefePrestamista.setRoles(rolesJefePrestamista);

	    // Guardar el nuevo usuario en la tabla usuario
	    usuariosrepo.save(nuevoUsuarioJefePrestamista);
	    
	    return nuevoJefePrestamista;
	}

}
