package com.ps.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;


public class BusRepositoryImpl implements BusRepository{
    public void createBuses(){
        try(Connection conn=DBConnectionManager.getDBConnection();
            Statement statement=conn.createStatement();
        ){

        }catch(NamingException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void getBuses(){
        try(Connection conn=DBConnectionManager.getDBConnection();
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select * from bus");
        ){

        }catch(NamingException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
