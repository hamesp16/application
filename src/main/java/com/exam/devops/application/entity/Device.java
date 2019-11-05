package com.exam.devops.application.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String modelName;
    private String serialNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER)
    private List<Measurement> measurements;

    public Device() {
    }

    public Device(String modelName, String serialNumber, List<Measurement> measurements) {
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.measurements = measurements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}
