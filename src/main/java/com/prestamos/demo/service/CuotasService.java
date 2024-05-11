package com.prestamos.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prestamos.demo.entity.Cuotas;
import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;

public interface CuotasService extends UserDetailsService {
	
	Cuotas save(Cuotas objCuotas);
	List<Cuotas> listarCuotas(Prestamos prestamos);

}
