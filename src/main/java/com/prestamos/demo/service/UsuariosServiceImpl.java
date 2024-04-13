package com.prestamos.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prestamos.demo.entity.Rol;
import com.prestamos.demo.entity.RolName;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService {

	@Autowired
	private UsuariosRepository repo;

	@Override
	public Usuarios newUsuarioJP(Usuarios objUsu) {
	    Rol rolJefePrestamista = new Rol();
	    rolJefePrestamista.setNombreRol(RolName.ROL_JEFE_PRESTAMISTA);
	    
	    // Establecer los roles del usuario
	    objUsu.setRoles(Collections.singletonList(rolJefePrestamista));
	    
	    // Crear el nuevo usuario
	    Usuarios usu = new Usuarios(objUsu.getNombre(), objUsu.getApellido(), objUsu.getCorreo(),
	            objUsu.getDni(), objUsu.getTelefono(), objUsu.getContrasenia(), objUsu.getNacimiento(), objUsu.getRoles());
	    
	    return repo.save(usu);
	}

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Usuarios user = repo.findUsuariosByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		user.getRoles()
				.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getNombreRol().name()))));
		
		user.getRoles().stream()
		.flatMap(role -> role.getPermisos().stream())
		.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getNombre_permiso())));
		
		return new User(user.getCorreo(),
				user.getContrasenia(),
				authorityList);
	}
	
	
	
}
