package com.ps.services;

import com.ps.repository.BusRepository;
import com.ps.repository.BusRepositoryImpl;

public class BusServices {
    public void getBusesService(){
        BusRepository busRepository=new BusRepositoryImpl();
        busRepository.getBuses();
    }
}
