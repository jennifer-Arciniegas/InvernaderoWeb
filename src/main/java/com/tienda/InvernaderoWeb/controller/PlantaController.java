package com.tienda.InvernaderoWeb.controller;


import com.tienda.InvernaderoWeb.dto.PlantaDTO;
import com.tienda.InvernaderoWeb.model.*;
import com.tienda.InvernaderoWeb.service.PlantaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
/**@RestController
public class IndexController {

    @RequestMapping("/")
    public String defaultPath(){
        return "Hello World!";
    }
**/
@Controller

@RequestMapping("/plantas")
public class PlantaController {

    private final PlantaService plantaService;
    private final List<String> tiposPlanta = List.of("Cactus", "Rosa", "Orquidea", "Suculenta");

    // Inyección de dependencias a través del constructor
    public PlantaController(PlantaService plantaService) {
        this.plantaService = plantaService;
    }

    /**
     * Muestra la página principal con formulario e inventario
     */
    @GetMapping
    public String mostrarPaginaPrincipal(Model model) {
        // Agrega los datos necesarios para la vista
        model.addAttribute("tiposPlanta", tiposPlanta); // Para el select del formulario
        model.addAttribute("plantaDTO", new PlantaDTO()); // DTO vacío para el formulario
        model.addAttribute("plantas", plantaService.obtenerTodasLasPlantas()); // Lista de plantas para la tabla
        return "index.html"; // Renderiza el template index.html
    }

    /**
     * Procesa el formulario de registro de plantas
     */
    @PostMapping("/registrar")
    public String registrarPlanta(
            @ModelAttribute PlantaDTO plantaDTO, // Recibe los datos del formulario
            RedirectAttributes redirectAttrs) { // Para enviar mensajes entre redirecciones

        try {
            // Crea la planta específica según el tipo
            Planta planta = crearPlantaSegunTipo(plantaDTO);
            // Guarda en la base de datos
            plantaService.guardarPlanta(planta);
            // Mensaje de éxito
            redirectAttrs.addFlashAttribute("mensaje", "¡Planta registrada con éxito!");
        } catch (IllegalArgumentException e) {
            // Mensaje de error específico
            redirectAttrs.addFlashAttribute("mensaje", "Error: " + e.getMessage());
        } catch (Exception e) {
            // Mensaje de error genérico
            redirectAttrs.addFlashAttribute("mensaje", "Error inesperado al registrar la planta");
        }

        // Redirige a la página principal
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
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al eliminar la planta: " + e.getMessage());
        }
        return "redirect:/plantas"; // Redirige para recargar la lista
    }

    /**
     * Método auxiliar para crear la planta específica según el tipo
     */
    private Planta crearPlantaSegunTipo(PlantaDTO plantaDTO) {
        // Validación básica
        if (plantaDTO.getTipo() == null || plantaDTO.getTipo().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de planta");
        }

        // Crea la instancia específica según el tipo
        return switch (plantaDTO.getTipo()) {
            case "Cactus" -> new Cactus(plantaDTO);
            case "Rosa" -> new Rosa(plantaDTO);
            case "Orquidea" -> new Orquidea(plantaDTO);
            case "Suculenta" -> new Suculenta(plantaDTO);
            default -> throw new IllegalArgumentException("Tipo de planta no válido: " + plantaDTO.getTipo());
        };
    }
}
