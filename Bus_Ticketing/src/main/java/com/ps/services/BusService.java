package com.ps.services;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.ps.domain.Bus;
import com.ps.repository.BusRepository;
import com.ps.repository.BusRepositoryImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusService {

    private static final Logger logger=LogManager.getLogger(BusService.class);

    public List<Bus> getBusesService(String date,String startTerminal,String endTerminal) throws SQLException, NamingException{
        logger.debug("called getBusservice: date: "+date+" startTerminal "+startTerminal+" endTerminal "+endTerminal);
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.getBuses(date,startTerminal,endTerminal);
    }

    public Bus createBusService(Bus bus) throws SQLException, NamingException{
        logger.debug("called createBusService: "+bus.getId());
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.createBus(bus);
    }

    public Bus updateBusService(String busId,String startTerminal,String endTerminal,String date){
        logger.debug("called updateBusService: "+busId);
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.updateBus(busId,startTerminal,endTerminal,date);
    }

    public Bus deleteBusService(String busId){
        logger.debug("called createBusService: "+busId);
        BusRepository busRepository=new BusRepositoryImpl();
        return busRepository.deleteBus(busId);
    }
}
