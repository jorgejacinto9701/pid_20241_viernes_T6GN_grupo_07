package com.prestamos.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prestamos.entity.Rol;
import com.prestamos.entity.Usuarios;
import com.prestamos.repository.UsuariosRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService {

	@Autowired
	private UsuariosRepository repo;

	@Override
	public Usuarios insertUsuario(Usuarios obj) {
		Usuarios usu = new Usuarios(obj.getNombre(), obj.getApellido(), obj.getCorreo(),
				obj.getDni(), obj.getTelefono(), obj.getContrasenia(), obj.getNacimiento(),Arrays.asList(new Rol("ROLE_USER")));
		return repo.save(obj);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
