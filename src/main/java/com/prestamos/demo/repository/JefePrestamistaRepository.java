package com.prestamos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prestamos.demo.entity.JefePrestamista;

@Repository
public interface JefePrestamistaRepository extends JpaRepository<JefePrestamista, Integer> {
    // Aquí puedes definir métodos adicionales si los necesitas
}
