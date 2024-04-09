package com.prestamos.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prestamos.entity.Usuarios;

public interface UsuariosService extends UserDetailsService{
	
	public Usuarios insertUsuario(Usuarios obj);
}
