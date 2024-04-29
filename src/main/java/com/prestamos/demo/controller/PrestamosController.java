package com.prestamos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.service.PrestamosServiceImp;

@Controller
public class PrestamosController {
	
	private final PrestamosServiceImp prestamoService;

    public PrestamosController(PrestamosServiceImp prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/solicitarPrestamo")
    public String solicitarPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamos());
        return "solicitarPrestamo";
    }

    @GetMapping("/calcular-prestamo/{monto}")
    public String calcularPrestamo(@PathVariable Double monto, Model model) {
        Prestamos prestamo = prestamoService.calcularPrestamo(monto);
        model.addAttribute("prestamo", prestamo);
        return "calcular-prestamo";
    }
}