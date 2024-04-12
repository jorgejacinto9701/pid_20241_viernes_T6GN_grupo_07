package com.prestamos.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.prestamos.demo.entity.Usuarios;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer>{
	
	 Optional <Usuarios> findUsuariosByCorreo(String correo);
	 
	 Optional<Usuarios> findByCorreoAndRolesNombreRol(String correo, String nombreRol);
		
}


