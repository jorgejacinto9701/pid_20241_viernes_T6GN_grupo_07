package com.prestamos.demo.entity;

import java.util.Collection;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="rol")
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_rol;
	
	@Column(name = "nombre_rol")
	@Enumerated(EnumType.STRING)
	private RolName nombre_rol;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="rol_permiso",
			joinColumns = @JoinColumn(name = "rol_id", referencedColumnName ="id_rol"),
			inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id_permiso")
			)
    private Set<Permisos> permisos;
	
	

	
}
