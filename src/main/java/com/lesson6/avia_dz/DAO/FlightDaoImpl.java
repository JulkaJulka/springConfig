package com.lesson6.avia_dz.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.model.Filter;
import com.lesson6.avia_dz.model.Flight;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightDaoImpl extends GenericDaoImpl<Flight> implements FlightDAO {
    private static String FIND_TOP_FLIGHTS_BY_CITY_TO = "SELECT * " +
            "FROM FLIGHT f " +
            "JOIN (" +
            "   SELECT fl.FLIGHT_ID AS fl_ID " +
            "   FROM FLIGHT fl " +
            "   JOIN FLIGHT_PASSENGER fp ON fl.FLIGHT_ID = fp.FLIGHT_ID" +
            "   WHERE fl.CITY_TO = ? " +
            "   GROUP BY fl.FLIGHT_ID " +
            "   ORDER BY COUNT(fl.CITY_TO) DESC) un ON f.FLIGHT_ID = un.fl_ID AND ROWNUM <= 20";

    private static String FIND_TOP_FLIGHTS_BY_CITY_FROM = "SELECT * " +
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
        query.setParameter(1, cityTo);
        return query.getResultList();
    }

    @Override
    public List<Flight> mostPopularFrom(String cityFrom) {
        Query query = getEntityManager().createNativeQuery(FIND_TOP_FLIGHTS_BY_CITY_FROM, Flight.class);
        query.setParameter(1, cityFrom);
        return query.getResultList();
    }

    //TODO


    @Override
    public List<Flight> flightsByDate(Filter filter) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> filterParams = objectMapper.convertValue(filter, Map.class);

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Flight> flightCriteria = criteriaBuilder.createQuery(Flight.class);

        Root<Flight> rootFlight = flightCriteria.from(Flight.class);

        Predicate predicate = criteriaBuilder.conjunction();

        for (String param : filterParams.keySet()) {
            if (filterParams.get(param) != null) {

                if (param.equals("model")) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                            rootFlight.get("plane").get(param), filterParams.get(param)));

                }
                if (param.equals("cityFrom") || param.equals("cityTo"))
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                            rootFlight.get(param), filterParams.get(param)));

                if (filter.getDateFlight() != null && param.equals("dateFlight"))
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder
                            .equal(rootFlight.<Date>get("dateFlight"), filter.getDateFlight()));

                // predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                //    rootFlight.get("dateFlight"), filterParams.get(param)));

                if (filter.getDateFrom() != null && filter.getDateTo() != null && param.equals("dateFrom")) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(rootFlight.<Date>get("dateFlight"),
                            filter.getDateFrom(), filter.getDateTo()));
                }
            }
        }
        CriteriaQuery<Flight> criteriaQuery = flightCriteria.select(rootFlight).where(predicate);
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
}
