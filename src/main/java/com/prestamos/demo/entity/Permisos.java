package com.prestamos.demo.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permisos")
public class Permisos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_permiso;
	
	@Column(name ="nombre_rol")
	private String nombre_permiso;
	
}
