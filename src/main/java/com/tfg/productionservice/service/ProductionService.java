package com.tfg.productionservice.service;

import com.tfg.productionservice.model.Production;
import com.tfg.productionservice.model.ProductionState;
import com.tfg.productionservice.repository.ProductionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
//import com.tfg.productionservice.event.StockCheckRequestedEvent;
//import com.tfg.productionservice.event.ProductionCompletedEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    // Given an ID and amount of Products create a Production
    public Production createProduction(int amount) {
        Production newProduction = new Production(
                amount, ProductionState.PENDING, LocalDateTime.now());

        // Publish on RabbitMQ for consume this event


        return productionRepository.save(newProduction);
    }

    // Given an ID of production from the RabbitMQ start a new production
    public void startProduction(Long id) {
        Production production = getProduction(id);
        production.start();
        // Update state in database
        productionRepository.save(production);

        try{
            // Simulate the production processing (30 seconds)
            Thread.sleep(30000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println(e);
        }

        // Apply the logic for a completed production when de production finish
        completeProduction(id);
    }

    // When the production is completed save production in repository
    // and publish an event on RabbitMQ
    public void completeProduction(Long id) {
        Production productionCompleted = getProduction(id);
        productionCompleted.complete();
        Production storedProduction = productionRepository.save(productionCompleted);

        // Method to send event to RabbitMQ
        publishProductionCompleted(storedProduction);

    }

    private void publishProductionCompleted(Production production) {
        // send event to RabbitMQ
    }

    // Get production by ID
    public Production getProduction(Long id) {
        Optional<Production> pro = productionRepository.findById(id);
        Production productionToReturn = pro.orElseThrow(()
                -> new RuntimeException("Production " + id + "not found"));

        return productionToReturn;
    }

    // Get all the production from the repository.
    // This query is to return to the user.
    public List<Production> getAllProductions() {
        return productionRepository.findAll();
    }
}