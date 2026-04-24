package com.tfg.productionservice.controller;

import com.tfg.productionservice.model.Production;
import com.tfg.productionservice.service.ProductionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @PostMapping
    public Production createProduction(@RequestParam int amount) {
        return productionService.createProduction(amount);
    }

    /*
    @PostMapping("/{id}/start")
    public Production startProduction(@PathVariable Long id) {
        return productionService.startProduction(id);
    }*/

    /*
    @PostMapping("/{id}/complete")
    public Production completeProduction(@PathVariable Long id) {
        return productionService.completeProduction(id);
    }*/

    @GetMapping("/{id}")
    public Production getProduction(@PathVariable Long id) {
        return productionService.getProduction(id);
    }

}
