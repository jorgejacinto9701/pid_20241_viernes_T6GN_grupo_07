package com.prestamos.demo.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="prestamos")
public class Prestamos {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPrestamo; 
	@Column(name = "monto")
	private Double monto;
	
	@Column(name = "fechainicio")
    private LocalDate fechaInicio;
	
	@Column(name = "fechafin")
    private LocalDate fechaFin;
	
	@Column(name = "pago")
    private Double pagoDiario;
	

    @ManyToOne
    @JoinColumn(name = "id")
    private Usuarios usuario;
	
	
}
