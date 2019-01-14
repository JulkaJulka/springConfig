package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.DAO.FlightDAO;
import com.lesson6.avia_dz.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl extends GenericsServiceImpl<Flight, FlightDAO> implements FlightService{
    @Autowired
    private FlightDAO flightDAO;

    @Override
    public Flight update(Flight entity) throws BadRequestException {
        if(findOne(entity.getId()) == null)
            throw new BadRequestException("Flight id " + entity.getId() + "doesn't exist in DB");
        return super.update(entity);
    }

    @Override
    public List<Flight> mostPopularTo(String cityTo) {
        return flightDAO.mostPopularTo(cityTo);
    }

    @Override
    public List<Flight> mostPopularFrom(String cityFrom) {
        return flightDAO.mostPopularFrom(cityFrom);
    }
}
