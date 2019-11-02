package com.exam.devops.application.controller;

import com.exam.devops.application.dao.DeviceRepository;
import com.exam.devops.application.dao.MeasurementRepository;
import com.exam.devops.application.entity.Device;
import com.exam.devops.application.entity.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;

    public DeviceController(DeviceRepository deviceRepository, MeasurementRepository measurementRepository) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices() {
        return ResponseEntity.status(200).body(deviceRepository.findAll());
    }

    @PostMapping(value = "/devices")
    public ResponseEntity<Integer> createDevice(@RequestBody Device device) {
        if (device.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).body(deviceRepository.save(device).getId());
    }

    @PostMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<Void> createMeasurement(@RequestBody Measurement measurement, @PathVariable String deviceId) {
        if (measurement.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        measurementRepository.save(measurement);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurements(@PathVariable String deviceId) {
        return ResponseEntity.status(200).body(measurementRepository.findAll());
    }
}
