package com.ps.domain;

import java.util.UUID;

public class Ticket {
    private String id;
    private double fare;
    private String date;
    private boolean isAvailable;
    private int seatNo;
    private String busId;
    private String name;
    private String bookedAt;

    public Ticket(String id, double fare, String date, boolean isAvailable, int seatNo, String busId,String name,String bookedAt) {
        this.setId(id);
        this.setFare(fare);
        this.setDate(date);
        this.setAvailable(isAvailable);
        this.setSeatNo(seatNo);
        this.setBusId(busId);
        this.setName(name);
        this.setBookedAt(bookedAt);
    }

    public Ticket(double fare, String date,String busId) {
        this.setId(UUID.randomUUID().toString());
        this.setFare(fare);
        this.setDate(date);
        this.setAvailable(true);
        this.setBusId(busId);
    }

    public String getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(String bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
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

    @Override
    public String toString() {
        return "Ticket [bookedAt=" + bookedAt + ", busId=" + busId + ", date=" + date + ", fare=" + fare + ", id=" + id
                + ", isAvailable=" + isAvailable + ", name=" + name + ", seatNo=" + seatNo + "]";
    }   
}
