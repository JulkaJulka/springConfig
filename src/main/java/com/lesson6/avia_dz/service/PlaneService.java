package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.model.Plane;

import java.util.List;

public interface PlaneService extends GenericService<Plane> {
    List<Plane> oldPlanes();
    List<Plane> regularPlane(int year);
}
