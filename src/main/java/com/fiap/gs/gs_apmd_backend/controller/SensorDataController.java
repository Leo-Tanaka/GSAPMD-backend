package com.fiap.gs.gs_apmd_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.gs.gs_apmd_backend.model.SensorData;
import com.fiap.gs.gs_apmd_backend.repository.SensorDataRepository;

@RestController
@RequestMapping("/api/sensors")
@CrossOrigin(origins = "*")
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;

    @GetMapping
    public ResponseEntity<List<SensorData>> getAllReadings() {
        List<SensorData> readings = repository.findAll();
        return ResponseEntity.ok(readings);
    }

    @PostMapping
    public ResponseEntity<SensorData> registerReading(@RequestBody SensorData data) {
        SensorData savedData = repository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
    }
}