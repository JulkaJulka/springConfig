package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.model.Plane;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PlaneDaoImpl extends GenericDaoImpl<Plane> implements PlaneDAO {
    private static String FIND_PLANES_OLDER = "SELECT p FROM Plane p WHERE p.yearProduced < ";
    private static String FIND_PLANES_BY_FLIGHTS_YEAR = "SELECT * " +
            "FROM PLANE p " +
            "JOIN (" +
            "   SELECT p.PLANE_ID AS p_ID " +
            "   FROM PLANE p " +
            "   JOIN FLIGHT f ON p.PLANE_ID = f.PLANE_ID" +
            "   WHERE TO_CHAR(f.DATE_FLIGHT,'YYYY') = ? " +
            "   GROUP BY p.PLANE_ID " +
            "   HAVING COUNT(*) > 300" +
            "  ) pl1 on p.PLANE_ID = pl1.p_ID ";

    @Override
    public List<Plane> oldPlanes() {
        Date currentDate = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy");
        String currentYear = sd.format(currentDate);

        String condition = currentYear + " - 20 ";

        return getEntityManager().createQuery(FIND_PLANES_OLDER + condition, Plane.class).getResultList();

    }

    @Override
    public List<Plane> regularPlanes(int year) {

        Query planes = getEntityManager().createNativeQuery(FIND_PLANES_BY_FLIGHTS_YEAR, Plane.class);
        planes.setParameter(1, year);

        return planes.getResultList();
    }
}
