package com.prestamos.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.prestamos.demo.entity.Distrito;

public interface DistritoRepository extends CrudRepository<Distrito, Integer> {
    // Aquí puedes definir métodos de consulta adicionales si es necesario
}
