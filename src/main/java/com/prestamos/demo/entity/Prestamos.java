package com.prestamos.demo.entity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prestamos", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Prestamos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	
	@Column(name="monto")
	private Double monto;
	
	@Column(name="fecha_inicio")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
	private Date fecha_inicio;
	
	@Column(name="fecha_fin")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
	private Date fecha_fin;
	
	@Column(name="dias")
	private int dias;
	
	@Column(name="pagodiario")
	private Double pagodiario;
	
	@ManyToOne
    @JoinColumn(name = "usuario_id")
	private Usuarios idUsuario;
	
	@Column(name="estado")
	private String estado;

	public Prestamos(Double monto, Date fecha_inicio, Date fecha_fin, int dias, Double pagodiario, Usuarios idUsuario,
			String estado) {
		super();
		this.monto = monto;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.dias = dias;
		this.pagodiario = pagodiario;
		this.idUsuario = idUsuario;
		this.estado = estado;
	}
}