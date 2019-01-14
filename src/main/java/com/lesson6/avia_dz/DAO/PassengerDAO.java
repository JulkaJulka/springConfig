package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDAO extends GenericDAO<Passenger> {
List<Passenger> regularPasengers(int year);
}

