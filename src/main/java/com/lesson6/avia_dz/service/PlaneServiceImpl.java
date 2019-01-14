package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.DAO.PlaneDAO;
import com.lesson6.avia_dz.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneServiceImpl extends GenericsServiceImpl<Plane, PlaneDAO> implements PlaneService {

    @Autowired
    PlaneDAO planeDAO;

    @Override
    public List<Plane> oldPlanes() {
        return planeDAO.oldPlanes();
    }

    @Override
    public List<Plane> regularPlane(int year) {
        return planeDAO.regularPlanes(year);
    }

    @Override
    public Plane update(Plane entity) throws BadRequestException {
        if (findOne(entity.getId()) == null)
            throw new BadRequestException("Plane id " + entity.getId() + " doesn't exist in DB");
        return super.update(entity);
    }
}

