package com.tienda.InvernaderoWeb.dto;

import lombok.Data;

/**
 * Objeto para transferir datos entre el formulario y el controlador
 * (No es una entidad de base de datos)
 */
@Data
public class PlantaDTO {
    private String tipo;       // Tipo de planta (Cactus, Rosa, etc.)
    private String nombre;     // Nombre de la planta
    private int cantidad;      // Cantidad en stock
    private double precioBase; // Precio base sin cálculos

    // Constructor vacío necesario para Thymeleaf
    public PlantaDTO() {}

    // Constructor completo para pruebas
    public PlantaDTO(String tipo, String nombre, int cantidad, double precioBase) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioBase = precioBase;
    }
}