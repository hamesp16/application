package com.exam.devops.application.controller;

import com.exam.devops.application.dao.DeviceRepository;
import com.exam.devops.application.dao.MeasurementRepository;
import com.exam.devops.application.entity.Device;
import com.exam.devops.application.entity.Measurement;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceRepository deviceRepository;
    private final MeasurementRepository measurementRepository;
    private final MeterRegistry meterRegistry;
    private Counter deviceCounter;
    private Counter measurementCounter;


    public DeviceController(DeviceRepository deviceRepository,
                            MeasurementRepository measurementRepository,
                            MeterRegistry meterRegistry) {
        this.deviceRepository = deviceRepository;
        this.measurementRepository = measurementRepository;
        this.meterRegistry = meterRegistry;
        initOrderCounters();
    }

    @Timed(description = "Time spent fetching all devices", longTask = true, value = "device.all")
    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices() {
        logger.info("Fetching all devices");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.warn("Something went wrong while simulating slow request with Thread.sleep", e);
            e.printStackTrace();
        }
        List<Device> devices = deviceRepository.findAll();
        deviceGauge(devices);
        return ResponseEntity.ok(devices);
    }

    @Timed(description = "Time spent creating a new device")
    @PostMapping(value = "/devices")
    public ResponseEntity<Integer> createDevice(@RequestBody Device device) {
        logger.info("Creating new device [{}]", device.toString());
        if (device.getId() != null) {
            //Logging to warn level only to show the level in the logs. Would log this to info normally
            logger.warn("device.Id is set. This can not be set when creating new device");
            return ResponseEntity.badRequest().build();
        }
        deviceCounter.increment();
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceRepository.save(device).getId());
    }

    @Timed(description = "Time spent creating new measurements for a device")
    @PostMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<Void> createMeasurement(@RequestBody Measurement measurement, @PathVariable Integer deviceId) {
        if (measurement.getId() != null) {
            //Logging to warn level only to show the level in the logs. Would log this to info normally
            logger.warn("measurement.Id is set. This can not be set when creating new measurement");
            return ResponseEntity.badRequest().build();
        }
        if (deviceId == null) {
            logger.info("Trying to create new measurement on deviceId [{}]", deviceId);
            return ResponseEntity.notFound().build();
        }
        Optional<Device> device = deviceRepository.findById(deviceId);
        if (device.isPresent()) {
            measurement.setDevice(device.get());
        } else {
            logger.info("Unable to find device with deviceId [{}]", deviceId);
            return ResponseEntity.notFound().build();
        }
        measurementRepository.save(measurement);
        logger.info("Measurement [{}] created", measurement.toString());
        measurementCounter.increment();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Timed(description = "Time spent fetching all measurements for a device")
    @GetMapping(value = "/devices/{deviceId}/measurements")
    public ResponseEntity<List<Measurement>> getMeasurements(@PathVariable Integer deviceId) {
        logger.info("Fetching all measurements for devide with id [{}]", deviceId);
        Optional<Device> device = deviceRepository.findById(deviceId);
        return device.map(value -> ResponseEntity.ok().body(value.getMeasurements())).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    private void initOrderCounters() {
        deviceCounter = Counter.builder("device.creations")
                .tag("type", "device")
                .description("The number of devices created")
                .register(meterRegistry);

        measurementCounter = Counter.builder("measurement.creations")
                .tag("type", "measurement")
                .description("The number of measurements created")
                .register(meterRegistry);
    }

    private void deviceGauge(List<Device> devices) {
        Gauge.builder("devices.devicesInDb", devices, Collection::size)
                .description("Number of devices in the database")
                .register(meterRegistry);
    }
}
