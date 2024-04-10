package com.prestamos.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prestamos.demo.entity.Usuarios;

public interface UsuariosRepository extends CrudRepository<Usuarios, Integer>{
	
	 Optional <Usuarios> findUsuariosByCorreo(String correo);
		
}


