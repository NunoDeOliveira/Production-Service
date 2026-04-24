package com.tfg.productionservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "productions")
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @Enumerated(EnumType.STRING)
    private ProductionState state;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Production() {}

    public Production(int amount, ProductionState state, LocalDateTime startTime) {
        this.amount = amount;
        this.state = state;
        this.startTime = startTime;
    }

    // Switch to PREPARING state and record the production start time
    public void start() {
        this.state = ProductionState.PREPARING;
        this.startTime = LocalDateTime.now();
    }

    // Switch to COMPLETED state and record the production start time
    public void complete() {
        this.state = ProductionState.COMPLETED;
        this.endTime = LocalDateTime.now();
    }

    // Switch to REJECTED state
    public void reject() {
        this.state = ProductionState.REJECTED;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductionState getState() {
        return state;
    }

    public void setState(ProductionState state) {
        this.state = state;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(int amount, ProductionState state, LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}