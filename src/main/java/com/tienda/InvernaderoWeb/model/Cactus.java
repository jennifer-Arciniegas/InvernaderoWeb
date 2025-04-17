package com.tienda.InvernaderoWeb.model;


import com.tienda.InvernaderoWeb.dto.PlantaDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad que representa un cactus en el sistema
 */
@Entity
@DiscriminatorValue("CACTUS") // Valor que identifica este tipo en la base de datos
public class Cactus extends Planta {

    // Constructor vacío requerido por JPA/Hibernate
    public Cactus() {}

    // Constructor desde DTO
    public Cactus(PlantaDTO plantaDTO) {
        this.nombre = plantaDTO.getNombre();
        this.cantidad = plantaDTO.getCantidad();
        this.precioBase = plantaDTO.getPrecioBase();
    }

    // Implementación del cálculo de precio para cactus (30% más caro)
    @Override
    public double calcularPrecioVenta() {
        return this.precioBase * 1.3;
    }

    // Devuelve el tipo específico de planta
    @Override
    public String getTipo() {
        return "Cactus";
    }
}