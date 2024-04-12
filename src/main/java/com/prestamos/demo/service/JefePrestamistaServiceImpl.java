package com.prestamos.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.JefePrestamista;
import com.prestamos.demo.repository.JefePrestamistaRepository;
import com.prestamos.demo.repository.UsuariosRepository;

@Service
public class JefePrestamistaServiceImpl implements JefePrestamistaService{

	@Autowired
    private JefePrestamistaRepository jefePrestamistaRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;
    
	@Override
	public JefePrestamista newJP(JefePrestamista obj) {
		
		return null;
	}

}
