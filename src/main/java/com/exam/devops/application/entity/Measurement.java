package com.exam.devops.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Long lat;
    private Long lng;
    private Long sievert;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public Measurement() {
    }

    public Measurement(Long lat, Long lng, Long sievert, Device device) {
        this.lat = lat;
        this.lng = lng;
        this.sievert = sievert;
        this.device = device;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public Long getSievert() {
        return sievert;
    }

    public void setSievert(Long sievert) {
        this.sievert = sievert;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", sievert=" + sievert +
                ", device=" + device +
                '}';
    }
}
