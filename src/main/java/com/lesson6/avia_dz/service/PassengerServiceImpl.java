package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.DAO.PassengerDAO;
import com.lesson6.avia_dz.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PassengerServiceImpl extends GenericsServiceImpl<Passenger, PassengerDAO> implements PassengerService {
    @Autowired
    private PassengerDAO passengerDao;

    @Override
    public List<Passenger> regularPassengers(int year) {
        return passengerDao.regularPasengers(year);
    }
}
