package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.model.Passenger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PassengerDaoImpl extends GenericDaoImpl<Passenger> implements PassengerDAO {
    private static String FIND_PASGS_BY_FLIGHTS_YEAR =  "SELECT * FROM PASSENGER p " +
            "JOIN (" +
            "   SELECT p.PAS_ID AS p_ID " +
            "   FROM PASSENGER p " +
            "   JOIN FLIGHT_PASSENGER fp ON p.PAS_ID = fp.PASSENGER_ID " +
            "   JOIN FLIGHT f ON f.FLIGHT_ID = fp.FLIGHT_ID " +
            "   WHERE TO_CHAR(f.DATE_FLIGHT,'YYYY') = ? " +
            "   GROUP BY p.PAS_ID " +
            "   HAVING COUNT(*) > 25" +
            "  ) ps1 on p.PAS_ID = ps1.p_ID ";

    @Override
    public List<Passenger> regularPasengers(int year) {
        Query query = getEntityManager().createNativeQuery(FIND_PASGS_BY_FLIGHTS_YEAR, Passenger.class);
        query.setParameter(1, year);
        return query.getResultList();
    }
}
