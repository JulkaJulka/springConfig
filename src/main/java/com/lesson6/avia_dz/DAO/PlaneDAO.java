package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.model.Plane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneDAO extends GenericDAO<Plane> {
    List<Plane> oldPlanes();
    List<Plane> regularPlanes(int year);
}
