package com.prestamos.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.prestamos.demo.entity.Usuarios;

public interface UsuariosService extends UserDetailsService{
	
	public Usuarios newUsuarioJP(Usuarios obj);
}
