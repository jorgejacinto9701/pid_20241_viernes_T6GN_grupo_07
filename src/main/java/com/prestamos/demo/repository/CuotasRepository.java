package com.prestamos.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.demo.entity.Cuotas;
import com.prestamos.demo.entity.Prestamos;

public interface CuotasRepository extends JpaRepository<Cuotas, Integer> {
	
	List<Cuotas> findByPrestamo(Prestamos prestamo);

}
