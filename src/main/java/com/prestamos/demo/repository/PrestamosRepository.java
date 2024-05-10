package com.prestamos.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;

public interface PrestamosRepository extends JpaRepository<Prestamos, Integer>{
	
	List<Prestamos> findByIdUsuario(Usuarios idUsuario);
	Optional<Prestamos> findById(Integer id);
	

}
