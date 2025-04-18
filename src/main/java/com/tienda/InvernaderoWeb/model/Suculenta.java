package com.tienda.InvernaderoWeb.model;


import com.tienda.InvernaderoWeb.dto.PlantaDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad que representa un Suculenta en el sistema
 */
@Entity
@DiscriminatorValue("Suculenta") // Valor que identifica este tipo en la base de datos
public class Suculenta extends Planta {

    // Constructor vacío requerido por JPA/Hibernate
    public Suculenta() {}

    // Constructor desde DTO
    public Suculenta(PlantaDTO plantaDTO) {
        this.nombre = plantaDTO.getNombre();
        this.cantidad = plantaDTO.getCantidad();
        this.precioBase = plantaDTO.getPrecioBase();
    }

    // Implementación del cálculo de precio para cactus (30% más caro)
    @Override
    public double calcularPrecioVenta() {
        return this.precioBase * 1.10;
    }

    // Devuelve el tipo específico de planta
    @Override
    public String getTipo() {
        return "Suculenta";
    }
}