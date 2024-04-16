package com.prestamos.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.demo.entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
	
Usuarios findByCorreo (String correo);
	
	Usuarios findById(int id);
	
	List<Usuarios> findByIdUsuario(Usuarios idUsuario);
		
}


