package com.ps.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Bus;
import com.ps.domain.Ticket;


public class BusRepositoryImpl implements BusRepository {
    public Bus createBus(Bus bus)throws SQLException,NamingException{
        try{
            Connection conn=DBConnectionManager.getDBConnection();
            PreparedStatement statement=conn.prepareStatement("insert into bus values(?,?,?,?,?,?,?,?,?)");
            statement.setString(1, bus.getId());
            statement.setString(2, bus.getRegistrationNumber());
            statement.setString(3, bus.getName());
            statement.setString(4, bus.getStartTerminal());
            statement.setString(5, bus.getEndTerminal());
            statement.setString(6, bus.getType());
            statement.setDate(7, Date.valueOf(bus.getDate()));
            statement.setInt(8, bus.getNoOfTickets());
            statement.setInt(9,bus.getRemainingTickets());
            
            if(statement.executeUpdate()>0){
                statement=conn.prepareStatement("insert into ticket values(?,?,?,?,?)");
                Ticket[] tickets=bus.getTickets();
                for(int i=0;i<tickets.length;i++){
                    statement.setString(1, tickets[i].getId());
                    statement.setDouble(2, tickets[i].getFare());
                    statement.setDate(3, Date.valueOf(tickets[i].getDate()));
                    statement.setBoolean(4, tickets[i].isAvailable());
                    statement.setString(5, tickets[i].getBusId());
                    statement.executeUpdate();
                }
            }
            return bus;

        }finally{

        }
    }

    public List<Bus> getBuses(String date,String startTerminal,String endTerminal)throws SQLException,NamingException{
        try(Connection conn=DBConnectionManager.getDBConnection();
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select * from bus where date='"+date+"' and startterminal='"+startTerminal+"' and endterminal='"+endTerminal+"'");
        ){
            List<Bus> buses=new ArrayList<Bus>();
            while(rs.next()){
                String id=rs.getString("id");
                String registrationNumber=rs.getString("registrationnumber");
                String name=rs.getString("name");
                String startTerminal1=rs.getString("startterminal");
                String endTerminal1=rs.getString("endterminal");
                String type=rs.getString("type");
                String date1=rs.getDate("date").toString();
                int noOfTickets=rs.getInt("nooftickets");
                int remainingTickets=rs.getInt("remainingtickets");

                Bus bus=new Bus(id,registrationNumber, name, startTerminal1, endTerminal1, type, date1, noOfTickets,remainingTickets);
                buses.add(bus);
            }
            return buses;
        }
    }
}
