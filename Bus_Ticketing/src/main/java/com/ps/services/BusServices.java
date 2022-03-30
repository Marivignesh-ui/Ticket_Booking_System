package com.ps.services;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Bus;
import com.ps.repository.BusRepository;
import com.ps.repository.BusRepositoryImpl;

public class BusServices {
    public List<Bus> getBusesService(String date,String startTerminal,String endTerminal) throws SQLException, NamingException{
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.getBuses(date,startTerminal,endTerminal);
    }

    public Bus createBusService(Bus bus) throws SQLException, NamingException{
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.createBus(bus);
    }
}
