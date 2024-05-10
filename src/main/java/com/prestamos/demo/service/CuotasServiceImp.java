package com.prestamos.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.Cuotas;
import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.CuotasRepository;
import com.prestamos.demo.repository.UsuariosRepository;

@Service
public class CuotasServiceImp implements CuotasService {
	
	@Autowired
	private CuotasRepository cuorepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cuotas save(Cuotas objCuotas) {
		
		  // Obtener el prestamo asociado a la cuota
	    Prestamos prestamo = objCuotas.getPrestamo();
	    
	    // Obtener el usuario que creó el préstamo
	    Usuarios usuarioPrestamo = prestamo.getIdUsuario();
	    
	    // Establecer el usuario en la cuota
	    objCuotas.setUsuario(usuarioPrestamo);
	    
	    // Establecer el estado como "SIN PAGAR"
	    objCuotas.setEstado("SIN PAGAR");
	    
	    // Obtener el monto del pagodiario del préstamo
	    double montoPagodiario = prestamo.getPagodiario();
	    
	    // Establecer el monto en la cuota
	    objCuotas.setMonto(montoPagodiario);
	    
	    // Guardar la cuota
	    return cuorepo.save(objCuotas);
	}
	

}
