package com.prestamos.demo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.Prestamos;



@Service
public class PrestamosServiceImp  implements PrestamoService{

	@Override
	public Prestamos calcularPrestamo(Double monto) {
		 Prestamos prestamo = new Prestamos();
	     prestamo.setMonto(monto);
	     prestamo.setFechaInicio(LocalDate.now());
	     prestamo.setFechaFin(prestamo.getFechaInicio().plusDays(30));
	     prestamo.setPagoDiario(prestamo.getMonto() / 26 * 0.10);
	     return prestamo;

	}

}
