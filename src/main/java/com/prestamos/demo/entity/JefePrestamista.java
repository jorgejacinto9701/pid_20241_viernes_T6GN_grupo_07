package com.prestamos.demo.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jefe_prestamista", uniqueConstraints = @UniqueConstraint(columnNames = "correo_jp"))
public class JefePrestamista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_usuario_jp; 
	
	@Column(name="nombres_jp")
	private String nombre_jp;
	
	@Column(name="apellido_jp")
    private String apellido_jp;
	
	@Column(name="correo_jp")
    private String correo_jp;
	
	
	@Column(name="dni_jp")
    private String dni_jp;
	
	@Column(name="telefono_jp")
    private String telefono_jp;
	
	@Column(name="contrasenia_jp")
    private String contrasenia_jp;
	
	@Column(name="nacimiento_jp")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date nacimiento_jp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuarios usuario;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
    private Distrito distrito;
}
