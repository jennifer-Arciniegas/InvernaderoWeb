package com.tienda.InvernaderoWeb.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_planta", discriminatorType = DiscriminatorType.STRING)
public abstract class Planta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nombre;
    public int cantidad;
    public double precioBase;

    public abstract double calcularPrecioVenta();
    public abstract String getTipo();

}