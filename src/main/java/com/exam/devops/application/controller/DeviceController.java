package com.exam.devops.application.controller;

import com.exam.devops.application.dao.DeviceRepository;
import com.exam.devops.application.dao.MeasurementRepository;
import com.exam.devops.application.entity.Device;
import com.exam.devops.application.entity.Measurement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(deviceRepository.findAll());
    }

    @PostMapping(value = "/devices")
    public ResponseEntity<Integer> createDevice(@RequestBody Device device) {
        if (device.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceRepository.save(device).getId());
    }

    @PostMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<Void> createMeasurement(@RequestBody Measurement measurement, @PathVariable Integer deviceId) {
        if (measurement.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        if (deviceId == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<Device> device = deviceRepository.findById(deviceId);
        if (device.isPresent()) {
            measurement.setDevice(device.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        measurementRepository.save(measurement);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurements(@PathVariable Integer deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if (!device.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(device.get().getMeasurements());
    }
}
