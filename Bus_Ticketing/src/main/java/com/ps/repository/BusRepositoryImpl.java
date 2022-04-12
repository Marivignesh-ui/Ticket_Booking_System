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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BusRepositoryImpl implements BusRepository {

    private static final Logger logger=LogManager.getLogger();

    public Bus createBus(Bus bus) {
        Connection conn=null;
        try{
            conn=DBConnectionManager.getDBConnection();
            logger.debug("DB Connection successful");

            PreparedStatement statement=conn.prepareStatement("insert into bus(id,registrationnumber,name,startterminal,endterminal,type,date,nooftickets,remainingtickets) values(?,?,?,?,?,?,?,?,?)");
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
                statement=conn.prepareStatement("insert into ticket(id,fare,date,isavailable,seat_no,busid) values(?,?,?,?,?,?)");
                Ticket[] tickets=bus.getTickets();
                for(int i=0;i<tickets.length;i++){
                    statement.setString(1, tickets[i].getId());
                    statement.setDouble(2, tickets[i].getFare());
                    statement.setDate(3, Date.valueOf(tickets[i].getDate()));
                    statement.setBoolean(4, tickets[i].isAvailable());
                    statement.setInt(5,tickets[i].getSeatNo());
                    statement.setString(6, tickets[i].getBusId());
                    statement.executeUpdate();
                }
            }
            logger.debug("returning response: "+bus);
            return bus;
        }catch(SQLException | NamingException e){
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException("Error creating bus");
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Bus> getBuses(String date,String startTerminal,String endTerminal)throws SQLException,NamingException{
        try(Connection conn=DBConnectionManager.getDBConnection();
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select * from bus where date='"+date+"' and startterminal='"+startTerminal+"' and endterminal='"+endTerminal+"'");
        ){
            logger.debug("DB Connection successful");
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
            logger.debug("returning response: "+buses);
            return buses;
        }catch(SQLException | NamingException e){
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException("Error creating bus");
        }
    }

    public Bus updateBus(String busId, String startTerminal, String endTerminal, String date){
        try(
            Connection conn=DBConnectionManager.getDBConnection();
        ){
            PreparedStatement statement=conn.prepareStatement("update bus set startterminal=?, endterminal=?, date=? where id=?");
            logger.debug("DB Connection successful");
            statement.setString(1, startTerminal);
            statement.setString(2, endTerminal);
            statement.setDate(3, Date.valueOf(date));
            statement.setString(4, busId);

            if(statement.executeUpdate()>0){
                statement=conn.prepareStatement("update ticket set date=?, isavailable=?, user_id=?, booked_at=? where busid=?");
                statement.setDate(1, Date.valueOf(date));
                statement.setBoolean(2, true);
                statement.setString(3, null);
                statement.setString(4, null);
                statement.setString(5, busId);

                if(statement.executeUpdate()>0){
                    statement=conn.prepareStatement("select * from bus where id=?");
                    statement.setString(1, busId);
                    ResultSet rSet=statement.executeQuery();

                    Bus bus=null;
                    while(rSet.next()){
                        String id=rSet.getString("id");
                        String registrationNumber=rSet.getString("registrationnumber");
                        String name=rSet.getString("name");
                        String startTerminal1=rSet.getString("startterminal");
                        String endTerminal1=rSet.getString("endterminal");
                        String type=rSet.getString("type");
                        String date1=rSet.getDate("date").toString();
                        int noOfTickets=rSet.getInt("nooftickets");
                        int remainingTickets=rSet.getInt("remainingtickets");
        
                        bus=new Bus(id,registrationNumber, name, startTerminal1, endTerminal1, type, date1, noOfTickets,remainingTickets);
                    }
                    logger.debug("returning response: "+bus);
                    return bus;
                }
            }

        }catch(SQLException | NamingException e){
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            throw new RuntimeException("Error updating bus data in DB");
        }
        return null;
    }

    public Bus deleteBus(String busId){
        try(
            Connection conn=DBConnectionManager.getDBConnection();
            ){
                PreparedStatement statement=conn.prepareStatement("select * from bus where id=?");
                statement.setString(1, busId);
                ResultSet rSet=statement.executeQuery();

                Bus bus=null;

                rSet.next();
                
                String id=rSet.getString("id");
                String registrationNumber=rSet.getString("registrationnumber");
                String name=rSet.getString("name");
                String startTerminal1=rSet.getString("startterminal");
                String endTerminal1=rSet.getString("endterminal");
                String type=rSet.getString("type");
                String date1=rSet.getDate("date").toString();
                int noOfTickets=rSet.getInt("nooftickets");
                int remainingTickets=rSet.getInt("remainingtickets");

                bus=new Bus(id,registrationNumber, name, startTerminal1, endTerminal1, type, date1, noOfTickets,remainingTickets);

            statement=conn.prepareStatement("delete from bus where id=?");
            statement.setString(1, busId);
            
            if(statement.executeUpdate()>0){
                return bus;
            }
        }catch(SQLException | NamingException e){
            logger.error(e);
            logger.catching(e);
            e.printStackTrace();
            throw new RuntimeException("Error getting data from DB");
        }
        return null;
    }

}
