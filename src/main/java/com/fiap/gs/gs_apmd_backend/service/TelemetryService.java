package com.fiap.gs.gs_apmd_backend.service;

import com.fiap.gs.gs_apmd_backend.dto.TelemetryDTO;
import com.fiap.gs.gs_apmd_backend.model.CriticalAlert;
import com.fiap.gs.gs_apmd_backend.model.Module;
import com.fiap.gs.gs_apmd_backend.model.OperationalEvent;
import com.fiap.gs.gs_apmd_backend.repository.CriticalAlertRepository;
import com.fiap.gs.gs_apmd_backend.repository.ModuleRepository;
import com.fiap.gs.gs_apmd_backend.repository.OperationalEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelemetryService {

    @Autowired
    private ModuleRepository moduleRepo;
    @Autowired
    private OperationalEventRepository eventRepo;
    @Autowired
    private CriticalAlertRepository alertRepo;

    @Transactional
    public OperationalEvent processIncomingTelemetry(TelemetryDTO dto) {
        // 1. Busca o módulo pelo nome. Se não existir, cria na hora.
        Module module = moduleRepo.findByName(dto.getModuleName())
                .orElseGet(() -> {
                    Module newModule = new Module();
                    newModule.setName(dto.getModuleName());
                    return moduleRepo.save(newModule);
                });

        // 2. Salva o evento operacional de rotina
        OperationalEvent event = new OperationalEvent();
        event.setModule(module);
        event.setSensorType(dto.getSensorType());
        event.setReadingValue(dto.getReadingValue());
        event.setStatus(dto.getStatus());
        eventRepo.save(event);

        // 3. Regra de Negócio: Se o status for CRITICO, gera um alerta isolado
        if ("CRITICO".equalsIgnoreCase(dto.getStatus())) {
            CriticalAlert alert = new CriticalAlert();
            alert.setModule(module);
            alert.setMessage("Falha Crítica no sensor: " + dto.getSensorType());
            alert.setCriticalValue(dto.getReadingValue());
            alertRepo.save(alert);
        }

        return event;
    }
}