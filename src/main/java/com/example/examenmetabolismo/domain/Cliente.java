package com.example.examenmetabolismo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "informacioncliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "talla")
    private Double talla;

    @Column(name = "tipoActividad")
    private String tipoActividad;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "GER")
    private Double GER;

    @Column(name = "GET")
    private Double GET;
}
