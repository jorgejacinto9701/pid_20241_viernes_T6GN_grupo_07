package com.prestamos.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.prestamos.demo.entity.Permisos;
import com.prestamos.demo.entity.Rol;
import com.prestamos.demo.entity.RolName;
import com.prestamos.demo.entity.Usuarios;
import com.prestamos.demo.repository.UsuariosRepository;

@SpringBootApplication
public class PrestamosGrupo7Application {
	
	@Autowired
    private UsuariosRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(PrestamosGrupo7Application.class, args);
	}
	
	@Bean
	CommandLineRunner init(UsuariosRepository repo) {
		return args ->{
			Permisos crudJefePrestamista = Permisos.builder()
					.nombre_permiso("CRUD_JEFE")
					.build();
			
			Permisos crudPrestamista = Permisos.builder()
					.nombre_permiso("CRUD_PRESTAMISTA")
					.build();
			
			Permisos crudPrestatario = Permisos.builder()
					.nombre_permiso("CRUD_PRESTATARIO")
					.build();
			
			Rol rolInversionista = Rol.builder()
					.nombre_rol(RolName.ROL_INVERSIONISTA)
					.permisos(Set.of(crudJefePrestamista))
					.build();
			
			Rol rolJefePrestamista = Rol.builder()
					.nombre_rol(RolName.ROL_JEFE_PRESTAMISTA)
					.permisos(Set.of(crudPrestamista))
					.build();
			
			Rol rolPrestamista = Rol.builder()
					.nombre_rol(RolName.ROL_PRESTAMISTA)
					.permisos(Set.of(crudPrestatario))
					.build();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date fecha1 = formatter.parse("1990-01-01");
			Date fecha2 = formatter.parse("1995-05-15");
			
			Usuarios userInversionista = Usuarios.builder()
					.nombre("Ricardo")
					.apellido("Sanchez")
					.correo("ricardo14@gmail.com")
					.dni("78945623")
					.telefono("987654321")
					.contrasenia("ricardo1234")
					.nacimiento(fecha1)		
					.roles(Set.of(rolInversionista))
					.build();

			Usuarios userInversionista2 = Usuarios.builder()
					.nombre("Carlos")
					.apellido("Ramirez")
					.correo("carlos17@gmail.com")
					.dni("76546541")
					.telefono("932654789")
					.contrasenia("carlos1234")
					.nacimiento(fecha2)		
					.roles(Set.of(rolInversionista))
					.build();
			
			repo.saveAll(List.of(userInversionista, userInversionista2));
		};
	}
}
