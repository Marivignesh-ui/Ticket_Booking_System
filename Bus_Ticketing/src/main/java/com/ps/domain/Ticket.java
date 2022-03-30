package com.ps.domain;

import java.util.UUID;

public class Ticket {
    private String id;
    private double fare;
    private String date;
    private boolean isAvailable;
    private String busId;

    public Ticket(double fare, String date,String busId) {
        this.setId(UUID.randomUUID().toString());
        this.setFare(fare);
        this.setDate(date);
        this.setAvailable(true);
        this.setBusId(busId);
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }   
}
