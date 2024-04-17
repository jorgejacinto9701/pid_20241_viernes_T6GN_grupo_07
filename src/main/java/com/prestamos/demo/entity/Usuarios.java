package com.prestamos.demo.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
public class Usuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
    private String apellido;
	
	@Column(name="correo")
    private String correo;
	
	
	@Column(name="dni")
    private String dni;
	
	@Column(name="telefono")
    private String telefono;
	
	@Column(name="contrasenia")
    private String contrasenia;
	
	@Column(name="nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios idUsuario;

    @ManyToOne
    @JoinColumn(name = "distrito_id")
    private Distrito distrito;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="usuario_rol",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName ="id"),
			inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
			)
    private Collection <Rol> roles;
	
	

	public Usuarios(String nombre, String apellido, String correo, String dni, String telefono, String contrasenia,
			Date nacimiento, Usuarios idUsuario, Distrito distrito, Collection<Rol> roles) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.dni = dni;
		this.telefono = telefono;
		this.contrasenia = contrasenia;
		this.nacimiento = nacimiento;
		this.idUsuario = idUsuario;
		this.distrito = distrito;
		this.roles = roles;
	}
    

}
