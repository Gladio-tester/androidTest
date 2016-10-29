package com.aquafadas.tag_the_bus.entities;

/**
 * Created by Yahya on 27/09/2016.
 */

public class Station {
    private String idStaion;
    private String street;
    private String buses;
    private double lat;
    private double lon;
    private double distance;

    public Station() {
    }

    public Station(String idStaion, String street, String buses, double lat, double lon, double distance) {
        this.idStaion = idStaion;
        this.street = street;
        this.buses = buses;
        this.lat = lat;
        this.lon = lon;
        this.distance = distance;
    }

    public String getIdStaion() {
        return idStaion;
    }

    public void setIdStaion(String idStaion) {
        this.idStaion = idStaion;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuses() {
        return buses;
    }

    public void setBuses(String buses) {
        this.buses = buses;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
