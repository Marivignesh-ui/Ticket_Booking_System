package com.ps.repository;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Ticket;

public interface TicketRepository {
    public List<Ticket> getTickets(String busId) throws SQLException, NamingException;
    public List<Ticket> bookTickets(List<String> ticketIds,String name,String busId) throws SQLException, NamingException;
    public Ticket getTicketById(String ticketId);
    public List<Ticket> getTicketsByUserId(String ticketId);
}
