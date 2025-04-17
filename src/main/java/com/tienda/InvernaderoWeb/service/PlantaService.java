package com.tienda.InvernaderoWeb.service;
// PlantaService.java

import com.tienda.InvernaderoWeb.model.Planta;
import com.tienda.InvernaderoWeb.repository.PlantaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlantaService {

    private final PlantaRepository plantaRepository;

    public PlantaService(PlantaRepository plantaRepository) {
        this.plantaRepository = plantaRepository;
    }

    public Planta guardarPlanta(Planta planta) {
        return plantaRepository.save(planta);
    }

    public List<Planta> obtenerTodasLasPlantas() {
        return plantaRepository.findAll();
    }

    public void eliminarPlanta(Long id) {
        if (!plantaRepository.existsById(id)) {
            throw new IllegalArgumentException("Planta con ID " + id + " no encontrada");
        }
        plantaRepository.deleteById(id);
    }
}