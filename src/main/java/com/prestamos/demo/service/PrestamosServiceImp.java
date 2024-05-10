package com.prestamos.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
		
		LocalDate fechaActual = LocalDate.now();
		
		Prestamos pres = new Prestamos(objPrestamo.getMonto(), objPrestamo.getFecha_inicio(), objPrestamo.getFecha_fin(), objPrestamo.getDias(), objPrestamo.getPagodiario(), usuarioActual, "PENDIENTE", fechaActual);
		
		return repoP.save(pres);
	}

	@Override
	public List<Prestamos> listarPrestamos(Usuarios usu) {
		// Obtener el usuario actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	    //Buscar al usuario prestatario actual en la base de datos
	    Usuarios usuarioPrestatario = repo.findByCorreo(currentUser.getUsername());
		
	    if (usuarioPrestatario != null && usuarioPrestatario.equals(usu)) {
	        return repoP.findByIdUsuario(usuarioPrestatario);
	    } else {
	        return Collections.emptyList(); // O podrías lanzar una excepción aquí
	    }
	}

	@Override
	public List<Prestamos> listarPrestamosPrestamista(Usuarios usuInversionista) {
	    // Obtener el usuario actualmente autenticado
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

	    // Buscar al usuario inversionista actual en la base de datos
	    Usuarios usuarioInversionista = repo.findByCorreo(currentUser.getUsername());

	    // Verificar si el usuario inversionista actual es el mismo que el proporcionado
	    if (usuarioInversionista != null && usuarioInversionista.equals(usuInversionista)) {
	        // Obtener los usuarios prestatarios asociados al usuario prestamista
	        List<Usuarios> prestatarios = repo.findByIdUsuario(usuarioInversionista);

	        // Lista para almacenar los préstamos asociados a los prestatarios
	        List<Prestamos> prestamos = new ArrayList<>();

	        // Recorrer todos los usuarios prestatarios y obtener sus préstamos
	        for (Usuarios prestatario : prestatarios) {
	            List<Prestamos> prestamosPrestatario = repoP.findByIdUsuario(prestatario);
	            prestamos.addAll(prestamosPrestatario);
	        }

	        // Devolver la lista de préstamos asociados a los prestatarios
	        return prestamos;
	    } else {
	        // Si el usuario actual no es el mismo que el proporcionado, retornar una lista vacía o lanzar una excepción, según tu lógica de negocio.
	        return Collections.emptyList(); // O podrías lanzar una excepción aquí
	    }
	}

	@Override
	public Prestamos obtenerId(int id) {
		return repoP.findById(id).orElse(null);
	}



}
