package com.ps.domain;

import java.util.UUID;

public class Bus{
    private String id;
    private String registrationNumber;
    private String name;
    private String startTerminal;
    private String endTerminal;
    private String type;
    private String date;
    private int noOfTickets;
    private Ticket[] tickets;
    private int remainingTickets; 

    public Bus(String registrationNumber,String name,String startTerminal,String endTerminal,String type,String date,int noOfTickets){
        UUID uniqueId=UUID.randomUUID();
        this.setId(uniqueId.toString());
        this.setRegistrationNumber(registrationNumber);
        this.setName(name);
        this.setStartTerminal(startTerminal);
        this.setEndTerminal(endTerminal);
        this.type=type;
        this.setDate(date);
        this.setNoOfTickets(noOfTickets);
        tickets=new Ticket[noOfTickets];
        double ticketFare=0.0;
        if(this.type.equals("premium")) ticketFare=400;
        if(this.type.equals("deluxe")) ticketFare=650;
        if(this.type.equals("ultra deluxe")) ticketFare=1200;
        for(int i=0;i<noOfTickets;i++){
            tickets[i]=new Ticket(ticketFare,date,this.getId());
        }
        this.setRemainingTickets(this.noOfTickets);
    }


    public Bus(String id,String registrationNumber,String name,String startTerminal,String endTerminal,String type,String date,int noOfTickets,int remainingTickets){
        this.setId(id);
        this.setRegistrationNumber(registrationNumber);
        this.setName(name);
        this.setStartTerminal(startTerminal);
        this.setEndTerminal(endTerminal);
        this.type=type;
        this.setDate(date);
        this.setNoOfTickets(noOfTickets);    
        this.setRemainingTickets(remainingTickets);
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public Ticket[] getTickets(){
        return tickets;
    }

    public String getType(){
        return this.type;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndTerminal() {
        return endTerminal;
    }

    public void setEndTerminal(String endTerminal) {
        this.endTerminal = endTerminal;
    }

    public String getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(String startTerminal) {
        this.startTerminal = startTerminal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bus [date=" + date + ", endTerminal=" + endTerminal + ", id=" + id + ", name=" + name + ", noOfTickets="
                + noOfTickets + ", registrationNumber=" + registrationNumber + ", remainingTickets=" + remainingTickets
                + ", startTerminal=" + startTerminal + ", type=" + type + "]";
    }
}