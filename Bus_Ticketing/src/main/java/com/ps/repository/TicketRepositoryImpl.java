package com.ps.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Ticket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicketRepositoryImpl implements TicketRepository {
    private static final Logger logger = LogManager.getLogger();

    public Ticket getTicketById(String ticketId) {
        Ticket ticket = null;
        try (
                Connection connection = DBConnectionManager.getDBConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from ticket where id=?");) {
            logger.debug("Database Connection Successful!!");
            preparedStatement.setString(1, ticketId);

            try (ResultSet rSet = preparedStatement.executeQuery();) {
                while (rSet.next()) {
                    String id = rSet.getString("id");
                    double fare = rSet.getDouble("fare");
                    String date = (rSet.getDate("date") != null) ? rSet.getDate("date").toString() : null;
                    boolean isAvailable = rSet.getBoolean("isavailable");
                    int seatNo = rSet.getInt("seat_no");
                    String busId1 = rSet.getString("busid");
                    String name = rSet.getString("user_id");
                    String bookedAt = (rSet.getDate("booked_at") == null) ? null : rSet.getDate("booked_at").toString();

                    ticket = new Ticket(id, fare, date, isAvailable, seatNo, busId1, name, bookedAt);
                }
            }
            logger.debug("returning response: "+ticket);
            return ticket;
        } catch (SQLException | NamingException e) {
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            throw new RuntimeException("Error getting data from DB");
        }
    }

    public List<Ticket> getTickets(String busId) {
        try (
                Connection connection = DBConnectionManager.getDBConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("select * from ticket where busid=? ORDER by seat_no");) {
            logger.debug("Database Connection Successful!!");
            preparedStatement.setString(1, busId);
            List<Ticket> tickets = new ArrayList<Ticket>();
            try (ResultSet rSet = preparedStatement.executeQuery();) {
                while (rSet.next()) {
                    String id = rSet.getString("id");
                    double fare = rSet.getDouble("fare");
                    String date = (rSet.getDate("date") != null) ? rSet.getDate("date").toString() : null;
                    boolean isAvailable = rSet.getBoolean("isavailable");
                    int seatNo = rSet.getInt("seat_no");
                    String busId1 = rSet.getString("busid");
                    String name = rSet.getString("user_id");
                    String bookedAt = (rSet.getDate("booked_at") == null) ? null : rSet.getDate("booked_at").toString();

                    Ticket ticket = new Ticket(id, fare, date, isAvailable, seatNo, busId1, name, bookedAt);
                    tickets.add(ticket);
                }
            }
            logger.debug("returning response: "+tickets);
            return tickets;
        } catch (SQLException | NamingException e) {
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            throw new RuntimeException("Error getting data from DB");
        }
    }

    public List<Ticket> getTicketsByUserId(String busId) {
        try (
                Connection connection = DBConnectionManager.getDBConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("select * from ticket where user_id=? ORDER by booked_at");) {
            logger.debug("Database Connection Successful!!");
            preparedStatement.setString(1, busId);
            List<Ticket> tickets = new ArrayList<Ticket>();
            try (ResultSet rSet = preparedStatement.executeQuery();) {
                while (rSet.next()) {
                    String id = rSet.getString("id");
                    double fare = rSet.getDouble("fare");
                    String date = (rSet.getDate("date") != null) ? rSet.getDate("date").toString() : null;
                    boolean isAvailable = rSet.getBoolean("isavailable");
                    int seatNo = rSet.getInt("seat_no");
                    String busId1 = rSet.getString("busid");
                    String name = rSet.getString("user_id");
                    String bookedAt = (rSet.getDate("booked_at") == null) ? null : rSet.getDate("booked_at").toString();

                    Ticket ticket = new Ticket(id, fare, date, isAvailable, seatNo, busId1, name, bookedAt);
                    tickets.add(ticket);
                }
            }
            logger.debug("returning response: "+tickets);
            return tickets;
        } catch (SQLException | NamingException e) {
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            throw new RuntimeException("Error getting data from DB");
        }
    }

    public List<Ticket> bookTickets(List<String> ticketIds, String name, String busId) {
        try (
                Connection connection = DBConnectionManager.getDBConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("update ticket set isavailable=?, user_id=?, booked_at=? where id=?");) {
            logger.debug("Database Connection Successful!!");
            int count = 0;
            for (String ticketId : ticketIds) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setString(2, name);
                Date date = new Date();
                preparedStatement.setObject(3, date);
                preparedStatement.setString(4, ticketId);
                count += preparedStatement.executeUpdate();
            }

            if (count > 0) {
                logger.debug("Tickets table Updated");
                int remainingTickets = 0;
                try (
                        Statement statement = connection.createStatement();
                        ResultSet rSet = statement
                                .executeQuery("select remainingtickets from bus where id='" + busId + "'");) {
                    while (rSet.next()) {
                        remainingTickets = rSet.getInt("remainingtickets");
                    }
                }
                logger.debug("Receieved data from bus Table");
                try (
                        PreparedStatement preparedStatement2 = connection
                                .prepareStatement("update bus set remainingtickets=? where id=?")) {
                    preparedStatement2.setInt(1, remainingTickets - ticketIds.size());
                    preparedStatement2.setString(2, busId);
                    preparedStatement2.executeUpdate();

                    List<Ticket> tickets = new ArrayList<>();
                    for (String ticketId : ticketIds) {
                        try (
                                Statement statement1 = connection.createStatement();
                                ResultSet rSet1 = statement1
                                        .executeQuery("select * from ticket where id='" + ticketId + "'");) {
                            while (rSet1.next()) {
                                String id = rSet1.getString("id");
                                double fare = rSet1.getDouble("fare");
                                String date = rSet1.getDate("date").toString();
                                boolean isAvailable = rSet1.getBoolean("isavailable");
                                int seatNo = rSet1.getInt("seat_no");
                                String busId1 = rSet1.getString("busid");
                                String name1 = rSet1.getString("user_id");
                                String bookedAt = rSet1.getDate("booked_at").toString();

                                Ticket ticket = new Ticket(id, fare, date, isAvailable, seatNo, busId1, name1,
                                        bookedAt);
                                tickets.add(ticket);
                            }
                        }
                    }
                    logger.debug("returning response: "+tickets);
                    return tickets;
                }
            }

        } catch (SQLException | NamingException e) {
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
        }
        return null;
    }
}
