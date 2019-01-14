package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerService  extends GenericService<Passenger>{
List<Passenger> regularPassengers(int year);
}
