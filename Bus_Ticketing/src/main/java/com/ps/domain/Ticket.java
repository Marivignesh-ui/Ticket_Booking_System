package com.ps.domain;

import java.util.UUID;

public class Ticket {
    private String id;
    private double fare;
    private String date;
    private boolean isAvailable;

    public Ticket(double fare, String date) {
        this.id=UUID.randomUUID().toString();
        this.fare = fare;
        this.date = date;
        this.isAvailable=true;
    }   
}
