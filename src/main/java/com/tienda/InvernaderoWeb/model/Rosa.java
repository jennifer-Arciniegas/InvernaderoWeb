package com.tienda.InvernaderoWeb.model;


import com.tienda.InvernaderoWeb.dto.PlantaDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entidad que representa una rosa en el sistema
 */
@Entity
@DiscriminatorValue("ROSA") // Valor que identifica este tipo en la base de datos
public class Rosa extends Planta {

    // Constructor vacío requerido por JPA/Hibernate
    public Rosa() {}

    // Constructor desde DTO
    public Rosa(PlantaDTO plantaDTO) {
        this.nombre = plantaDTO.getNombre();
        this.cantidad = plantaDTO.getCantidad();
        this.precioBase = plantaDTO.getPrecioBase();
    }

    // Implementación del cálculo de precio para rosas (precio base sin modificación)
    @Override
    public double calcularPrecioVenta() {
        return this.precioBase * 1.20; // Rosas se venden al precio base
    }

    // Devuelve el tipo específico de planta
    @Override
    public String getTipo() {
        return "Rosa";
    }
}