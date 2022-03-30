package com.ps.repository;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Bus;

public interface BusRepository {
    public Bus createBus(Bus bus) throws SQLException, NamingException;
    public List<Bus> getBuses(String date,String startTerminal,String endTerminal) throws SQLException, NamingException;
}
