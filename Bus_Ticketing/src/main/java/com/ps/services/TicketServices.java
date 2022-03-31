package com.ps.services;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Ticket;
import com.ps.repository.TicketRepository;
import com.ps.repository.TicketRepositoryImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketServices {
    private static final Logger logger=LogManager.getLogger();
    public List<Ticket> getTicketsService(String busId) throws SQLException, NamingException{
        logger.debug("TicketService Called!! about to Call implementation");
        TicketRepository ticketRepository=new TicketRepositoryImpl();
        return ticketRepository.getTickets(busId);
    }
    public List<Ticket> bookTicketService(List<String> ticketIds,String name,String busId)throws SQLException, NamingException{
        TicketRepository ticketRepository=new TicketRepositoryImpl();
        return ticketRepository.bookTickets(ticketIds, name, busId);
    }
}
