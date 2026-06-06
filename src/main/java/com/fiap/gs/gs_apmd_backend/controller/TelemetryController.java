package com.fiap.gs.gs_apmd_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.gs.gs_apmd_backend.dto.TelemetryDTO;
import com.fiap.gs.gs_apmd_backend.model.OperationalEvent;
import com.fiap.gs.gs_apmd_backend.repository.OperationalEventRepository;
import com.fiap.gs.gs_apmd_backend.service.TelemetryService;

@RestController
@RequestMapping("/api/telemetry")
@CrossOrigin(origins = "*")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @Autowired
    private OperationalEventRepository eventRepository;

    // GET com paginação e ordenação automática
    @GetMapping
    public ResponseEntity<Page<OperationalEvent>> getTelemetryHistory(
            @PageableDefault(size = 20, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        
        Page<OperationalEvent> events = eventRepository.findAll(pageable);
        return ResponseEntity.ok(events);
    }

    // POST recebendo o DTO e passando para o Service processar
    @PostMapping
    public ResponseEntity<OperationalEvent> receiveTelemetry(@RequestBody TelemetryDTO telemetryDTO) {
        OperationalEvent savedEvent = telemetryService.processIncomingTelemetry(telemetryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
}