package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.model.Filter;
import com.lesson6.avia_dz.model.Flight;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService extends GenericService<Flight> {
    List<Flight> flightsByDate(Filter filter) ;
    List<Flight> mostPopularTo(String cityTo);
    List<Flight> mostPopularFrom(String cityFrom);
}
