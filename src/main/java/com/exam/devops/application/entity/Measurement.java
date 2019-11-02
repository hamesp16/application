package com.exam.devops.application.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Measurement {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer deviceId;
    private String lat;
    private String lng;
    private String sievert;

    public Measurement() {
    }

    public Measurement(Integer deviceId, String lat, String lng, String sievert) {
        this.deviceId = deviceId;
        this.lat = lat;
        this.lng = lng;
        this.sievert = sievert;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getSievert() {
        return sievert;
    }

    public void setSievert(String sievert) {
        this.sievert = sievert;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", sievert='" + sievert + '\'' +
                '}';
    }
}
