package com.prestamos.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import com.prestamos.demo.entity.Usuarios;

public interface UsuariosService extends UserDetailsService{
	
Usuarios save(Usuarios objUsu);
	
	Usuarios saveJP(Usuarios objUsu, UserDetails currentUser);
	
	Usuarios saveP(Usuarios objUsu, UserDetails currentUser);
	
	Usuarios savePT(Usuarios objUsu, UserDetails currentUser);
	
	List<Usuarios> listJefePrestamista(Usuarios usuInversionista);
	
	Usuarios obtenerId(int id);
	
	Usuarios updateUsuario(Usuarios usuario);
	
}
