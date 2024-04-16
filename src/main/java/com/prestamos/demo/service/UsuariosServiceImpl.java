package com.prestamos.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prestamos.demo.entity.Distrito;
import com.prestamos.demo.entity.Rol;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService {

	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Autowired
	private UsuariosRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios usu = repo.findByCorreo(username);
		if( usu == null) {
			throw new UsernameNotFoundException("Correo o password incorrecto :(");
		}
		
		return new User(usu.getCorreo(),usu.getContrasenia(),mapRolesAuthorities(usu.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Rol> roles) {
	    return roles.stream()
	                .map(role -> new SimpleGrantedAuthority(role.getNombreRol()))
	                .collect(Collectors.toList());
	}


	@Override
	public Usuarios save(Usuarios objUsu) {
		 // Crear un nuevo usuario inversionista
	    Usuarios usu = new Usuarios(objUsu.getNombre(), objUsu.getApellido(), objUsu.getCorreo(), objUsu.getDni(),
	                            objUsu.getTelefono(), passEncoder.encode(objUsu.getContrasenia()), objUsu.getNacimiento(), null, null, Arrays.asList(new Rol("ROLE_INVERSIONISTA")));
	    
	    // Guardar el usuario
	    return repo.save(usu);
	}

	@Override
	public Usuarios saveJP(Usuarios objUsu, UserDetails currentUser) {
		// Obtener el usuario que está autenticado
	    Usuarios usuarioActual = repo.findByCorreo(currentUser.getUsername());
	    
	    // Crear un nuevo usuario Jefe Prestamista
	    Usuarios usu = new Usuarios(objUsu.getNombre(), objUsu.getApellido(), objUsu.getCorreo(), objUsu.getDni(),
	                            objUsu.getTelefono(), passEncoder.encode(objUsu.getContrasenia()), objUsu.getNacimiento(), usuarioActual, objUsu.getDistrito(), Arrays.asList(new Rol("ROLE_JEFE_PRESTAMISTA")));
	    
	    // Guardar el usuario
	    return repo.save(usu);
	}

	@Override
	public Usuarios saveP(Usuarios objUsu, UserDetails currentUser) {
		// Obtener el usuario que está autenticado
	    Usuarios usuarioActual = repo.findByCorreo(currentUser.getUsername());
	    
	 // Obtener el distrito del usuario actual
	    Distrito distritoUsuarioActual = usuarioActual.getDistrito();
	    
	    // Crear un nuevo usuario Jefe Prestamista
	    Usuarios usu = new Usuarios(objUsu.getNombre(), objUsu.getApellido(), objUsu.getCorreo(), objUsu.getDni(),
	                            objUsu.getTelefono(), passEncoder.encode(objUsu.getContrasenia()), objUsu.getNacimiento(), usuarioActual, distritoUsuarioActual, Arrays.asList(new Rol("ROLE_PRESTAMISTA")));
	    
	    // Guardar el usuario
	    return repo.save(usu);
	}

	@Override
	public Usuarios savePT(Usuarios objUsu, UserDetails currentUser) {
		// Obtener el usuario que está autenticado
	    Usuarios usuarioActual = repo.findByCorreo(currentUser.getUsername());
	    
	 // Obtener el distrito del usuario actual
	    Distrito distritoUsuarioActual = usuarioActual.getDistrito();
	    
	    // Crear un nuevo usuario Prestatario con el mismo distrito del usuario actual
	    Usuarios usu = new Usuarios(objUsu.getNombre(), objUsu.getApellido(), objUsu.getCorreo(), objUsu.getDni(),
	                                objUsu.getTelefono(), passEncoder.encode(objUsu.getContrasenia()), objUsu.getNacimiento(), usuarioActual, distritoUsuarioActual, Arrays.asList(new Rol("ROLE_PRESTATARIO")));
	    
	    // Guardar el usuario
	    return repo.save(usu);
	}

	@Override
	public List<Usuarios> listJefePrestamista(Usuarios usuInversionista) {
		// Obtener el usuario actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	    // Buscar al usuario inversionista actual en la base de datos
	    Usuarios usuarioInversionista = repo.findByCorreo(currentUser.getUsername());

	    // Verificar si el usuario inversionista actual es el mismo que el proporcionado
	    if (usuarioInversionista != null && usuarioInversionista.equals(usuInversionista)) {
	        // Retornar los jefes prestamistas asociados al usuario inversionista
	        return repo.findByIdUsuario(usuarioInversionista);
	    } else {
	        // Si el usuario actual no es el mismo que el proporcionado, retornar una lista vacía o lanzar una excepción, dependiendo de tu lógica de negocio.
	        return Collections.emptyList(); // O podrías lanzar una excepción aquí
	    }
	}

	@Override
	public Usuarios obtenerId(int id) {
		return repo.findById(id);
	}

	@Override
	public Usuarios updateUsuario(Usuarios usuario) {
		return repo.save(usuario);
	}


}
