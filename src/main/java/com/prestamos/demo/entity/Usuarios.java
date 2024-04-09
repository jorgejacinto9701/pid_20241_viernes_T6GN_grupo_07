package com.prestamos.demo.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
public class Usuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_usuario; 
	
	@Column(name="nombres")
	private String nombre;
	
	@Column(name="apellido")
    private String apellido;
	
	@Column(name="correo")
    private String correo;
	
	public Usuarios(String nombre, String apellido, String correo, String dni, String telefono,
			String contrasenia, Date nacimiento, Collection<Rol> roles) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.dni = dni;
		this.telefono = telefono;
		this.contrasenia = contrasenia;
		this.nacimiento = nacimiento;
		this.roles = roles;
	}
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
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name="usuarios_rol",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName ="id_usuario"),
			inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id_rol")
			)
    private Collection <Rol> roles;
    

    public Usuarios() {
	}
    
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Collection<Rol> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
    

}
