package com.prestamos.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "usuario_prestamista")
public class UsuarioPrestamista {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "usuario_id")
	    private Usuarios usuario_p;

	    @ManyToOne
	    @JoinColumn(name = "rol_id")
	    private Rol rol_p;
	    
	    @Column(name = "zona")
	    private String zona_p;

		public UsuarioPrestamista(Long id, Usuarios usuario, Rol rol, String zona) {
			super();
			this.id = id;
			this.usuario_p = usuario;
			this.rol_p = rol;
			this.zona_p = zona;
		}
	    
}