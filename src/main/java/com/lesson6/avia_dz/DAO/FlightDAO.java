package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.model.Filter;
import com.lesson6.avia_dz.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDAO extends GenericDAO<Flight> {
    List<Flight> flightsByDate(Filter filter);
    List<Flight> mostPopularTo(String cityTo);
    List<Flight> mostPopularFrom(String cityFrom);
}
