package com.fiap.gs.gs_apmd_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_sensor_data")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SensorData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String moduleName; // Ex: Suporte de Vida, Navegação

    @Column(nullable = false)
    private String sensorType; // Ex: Temperatura, Pressão, Radiação

    @Column(nullable = false)
    private Double readingValue;

    @Column(nullable = false)
    private String status; // Ex: NORMAL, ALERTA, CRITICO

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

}
