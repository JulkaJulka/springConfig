package com.lesson6.avia_dz.service;

import com.lesson6.avia_dz.BadRequestException;

import java.io.Serializable;

public interface GenericService<T> {

    T findOne(Serializable id);

    T save(T entity);

    T delete(Serializable id) throws BadRequestException;

    T update(T entity) throws BadRequestException;
}
