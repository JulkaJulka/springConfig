package com.lesson6.avia_dz.service;


import com.lesson6.avia_dz.BadRequestException;
import com.lesson6.avia_dz.DAO.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;

public  class GenericsServiceImpl<T, V extends GenericDAO<T>> implements GenericService<T> {

    @Autowired
    private V genericDao;

    @Override
    public T findOne(Serializable id) {
        return genericDao.findOne(id);
    }

    @Override
    public T save(T entity) {
        return genericDao.save(entity);
    }

    @Override
    public T delete(Serializable id) throws BadRequestException {
        checkExistenceEntityInDB(id);
        return genericDao.delete(id);
    }

    @Override
    public T update(T entity) throws BadRequestException {
        return  genericDao.update(entity);

    }

    public boolean checkExistenceEntityInDB(Serializable id) throws BadRequestException{
        T findEntity = (T)findOne( id);
        if(findEntity == null) throw new BadRequestException(  " with id " + id + "doesn't exist in DB" );
        return true;
    }
}

