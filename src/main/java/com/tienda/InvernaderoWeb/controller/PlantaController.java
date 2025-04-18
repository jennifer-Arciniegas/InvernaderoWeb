package com.tienda.InvernaderoWeb.controller;

import com.tienda.InvernaderoWeb.dto.PlantaDTO;
import com.tienda.InvernaderoWeb.model.*;
import com.tienda.InvernaderoWeb.service.PlantaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/plantas")
public class PlantaController {

    private final PlantaService plantaService;
    private static final List<String> TIPOS_PLANTA = List.of("Cactus", "Rosa", "Orquidea", "Suculenta");

    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    /**
     * Muestra la página principal con formulario e inventario
     */
    @GetMapping
    public String mostrarPaginaPrincipal(Model model) {
        model.addAttribute("tiposPlanta", TIPOS_PLANTA);
        model.addAttribute("plantaDTO", new PlantaDTO());
        model.addAttribute("plantas", plantaService.obtenerTodasLasPlantas());
        return "index";
    }

    /**
     * Procesa el formulario de registro de plantas
     */
    @PostMapping("/registrar")
    public String registrarPlanta(
            @ModelAttribute PlantaDTO plantaDTO,
            RedirectAttributes redirectAttrs) {

        try {
            Planta planta = crearPlantaSegunTipo(plantaDTO);
            plantaService.guardarPlanta(planta);
            redirectAttrs.addFlashAttribute("mensaje", "¡Planta registrada con éxito!");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error: " + e.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error inesperado al registrar la planta");
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }

        return "redirect:/plantas";
    }

    /**
     * Elimina una planta por su ID
     */
    @PostMapping("/eliminar/{id}")
    public String eliminarPlanta(
            @PathVariable Long id,
            RedirectAttributes redirectAttrs) {

        try {
            plantaService.eliminarPlanta(id);
            redirectAttrs.addFlashAttribute("mensaje", "Planta eliminada correctamente");
            redirectAttrs.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al eliminar la planta: " + e.getMessage());
            redirectAttrs.addFlashAttribute("tipoMensaje", "danger");
        }
        return "redirect:/plantas";
    }

    /**
     * Método auxiliar para crear la planta específica según el tipo
     */
    private Planta crearPlantaSegunTipo(PlantaDTO plantaDTO) {
        if (plantaDTO.getTipo() == null || plantaDTO.getTipo().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de planta");
        }

        return switch (plantaDTO.getTipo()) {
            case "Cactus" -> new Cactus(plantaDTO);
            case "Rosa" -> new Rosa(plantaDTO);
            case "Orquidea" -> new Orquidea(plantaDTO);
            case "Suculenta" -> new Suculenta(plantaDTO);
            default -> throw new IllegalArgumentException("Tipo de planta no válido: " + plantaDTO.getTipo());
        };
    }
}