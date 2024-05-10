package com.prestamos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.demo.entity.Cuotas;

public interface CuotasRepository extends JpaRepository<Cuotas, Integer> {

}
