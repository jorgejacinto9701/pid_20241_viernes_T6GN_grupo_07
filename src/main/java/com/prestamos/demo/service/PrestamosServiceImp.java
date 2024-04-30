package com.prestamos.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prestamos.demo.entity.Prestamos;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.PrestamosRepository;
import com.prestamos.demo.repository.UsuariosRepository;


@Service
public class PrestamosServiceImp implements PrestamosService {
	
	@Autowired
	private PrestamosRepository repoP;
	
	@Autowired
	private UsuariosRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prestamos save(Prestamos objPrestamo, UserDetails currentUser) {
		Usuarios usuarioActual = repo.findByCorreo(currentUser.getUsername());
		
		Prestamos pres = new Prestamos(objPrestamo.getMonto(), objPrestamo.getFecha_inicio(), objPrestamo.getFecha_fin(), objPrestamo.getDias(), objPrestamo.getPagodiario(), usuarioActual, "PENDIENTE");
		
		return repoP.save(pres);
	}

	@Override
	public List<Prestamos> listarPrestamos(Usuarios usu) {
		// Obtener el usuario actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	    //Buscar al usuario prestatario actual en la base de datos
	    Usuarios usuarioPrestatario = repo.findByCorreo(currentUser.getUsername());
		
	    // Verificar si el usuario inversionista actual es el mismo que el proporcionado
	    if (usuarioPrestatario != null && usuarioPrestatario.equals(usu)) {
	        // Retornar los jefes prestamistas asociados al usuario inversionista
	        return repoP.findByIdUsuario(usuarioPrestatario);
	    } else {
	        // Si el usuario actual no es el mismo que el proporcionado, retornar una lista vacía o lanzar una excepción, dependiendo de tu lógica de negocio.
	        return Collections.emptyList(); // O podrías lanzar una excepción aquí
	    }
	}

}
