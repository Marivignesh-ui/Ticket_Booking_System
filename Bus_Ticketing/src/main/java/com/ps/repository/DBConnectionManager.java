package com.ps.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionManager {
    static Connection getDBConnection()throws NamingException,SQLException{
        Context context = new InitialContext();
        DataSource ds=(DataSource)context.lookup("java:comp/env/jdbc/Bus_Ticketing");
        return ds.getConnection();
    }
}
