package com.lesson6.avia_dz.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.model.Filter;
import com.lesson6.avia_dz.model.Flight;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class FlightDaoImpl extends GenericDaoImpl<Flight> implements FlightDAO{
    private static String FIND_TOP_FLIGHTS_BY_CITY_TO =  "SELECT * " +
            "FROM FLIGHT f " +
            "JOIN (" +
            "   SELECT fl.FLIGHT_ID AS fl_ID " +
            "   FROM FLIGHT fl " +
            "   JOIN FLIGHT_PASSENGER fp ON fl.FLIGHT_ID = fp.FLIGHT_ID" +
            "   WHERE fl.CITY_TO = ? " +
            "   GROUP BY fl.FLIGHT_ID " +
            "   ORDER BY COUNT(fl.CITY_TO) DESC) un ON f.FLIGHT_ID = un.fl_ID AND ROWNUM <= 20";

    private static String FIND_TOP_FLIGHTS_BY_CITY_FROM =  "SELECT * " +
            "FROM FLIGHT f " +
            "JOIN (" +
            "   SELECT fl.FLIGHT_ID AS fl_ID " +
            "   FROM FLIGHT fl " +
            "   JOIN FLIGHT_PASSENGER fp ON fl.FLIGHT_ID = fp.FLIGHT_ID" +
            "   WHERE fl.CITY_FROM = ? " +
            "   GROUP BY fl.FLIGHT_ID " +
            "   ORDER BY COUNT(fl.CITY_TO) DESC) un ON f.FLIGHT_ID = un.fl_ID AND ROWNUM <= 20";

    @Override
    public List<Flight> mostPopularTo(String cityTo) {
        Query query = getEntityManager().createNativeQuery(FIND_TOP_FLIGHTS_BY_CITY_TO, Flight.class);
        query.setParameter(1,cityTo);
        return query.getResultList();
    }

    @Override
    public List<Flight> mostPopularFrom(String cityFrom) {
        Query query = getEntityManager().createNativeQuery(FIND_TOP_FLIGHTS_BY_CITY_FROM, Flight.class);
        query.setParameter(1,cityFrom);
        return query.getResultList();
    }

    //TODO
    @Override
    public List<Flight> flightsByDate(Filter filter) {
        return null;
    }
}
