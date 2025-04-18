package com.tienda.InvernaderoWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // Esto buscará index.html en templates/
    }
}
