package com.prestamos.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prestamos.demo.entity.Cuotas;

public interface CuotasService extends UserDetailsService {
	
	Cuotas save(Cuotas objCuotas);

}
