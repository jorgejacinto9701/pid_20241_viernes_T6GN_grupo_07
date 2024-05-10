package com.prestamos.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;

public interface PrestamosService extends UserDetailsService{
	
	Prestamos save(Prestamos objPrestamo, UserDetails currentUser );
	List<Prestamos> listarPrestamos(Usuarios usu);
	List<Prestamos>listarPrestamosPrestamista(Usuarios usu);
	Prestamos obtenerId(int id);

}
