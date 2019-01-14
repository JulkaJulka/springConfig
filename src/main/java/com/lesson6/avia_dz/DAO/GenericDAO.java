package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.BadRequestException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import javax.persistence.EntityManager;
import java.io.Serializable;


public interface GenericDAO<T>  {
    T findOne(Serializable id);

    T save(T entity);

    T delete(Serializable id) throws BadRequestException;

    T update(T entity) throws BadRequestException;


}
