package com.lesson6.avia_dz.DAO;

import com.lesson6.avia_dz.BadRequestException;
import org.springframework.core.GenericTypeResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;


public class GenericDaoImpl<T>  implements GenericDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private final Class<T> type;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public GenericDaoImpl() {
        this.type = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GenericDaoImpl.class);
    }

    @Override
    public T findOne(Serializable id) {
        return entityManager.find(type, id);
    }

    @Override
    @Transactional
    public T save(Object entity) {
        entityManager.persist(entity);
        return (T)entity;
    }

    @Override
    @Transactional
    public T delete(Serializable id) throws BadRequestException {

        if (entityManager.find(type, id) == null)
            throw new BadRequestException("Item id " + id + " doesn't exist in DB");

        T deleteEntity = findOne(id);
        entityManager.remove(deleteEntity);

        return deleteEntity;
    }

    @Transactional
    @Override
    public T update(Object entity)  {
        return entityManager.merge( (T)entity);
    }
}


