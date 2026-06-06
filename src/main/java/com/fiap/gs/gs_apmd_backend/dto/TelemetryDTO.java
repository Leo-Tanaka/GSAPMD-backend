package com.fiap.gs.gs_apmd_backend.dto;
import lombok.Data;

@Data
public class TelemetryDTO {
    private String moduleName;
    private String sensorType;
    private Double readingValue;
    private String status;
}