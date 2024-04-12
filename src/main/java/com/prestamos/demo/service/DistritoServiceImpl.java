package com.prestamos.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.Distrito;
import com.prestamos.demo.repository.DistritoRepository;

@Service
public class DistritoServiceImpl implements DistritoService {

	@Autowired
	private DistritoRepository disrepo;
	
	@Override
	public List<Distrito> listaDistritos() {
		// TODO Auto-generated method stub
		return (List<Distrito>) disrepo.findAll();
	}

}
