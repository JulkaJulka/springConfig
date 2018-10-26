package com.lesson3.hwStorageFile.dao;

import com.lesson3.hwStorageFile.model.Storage;

public class StorageDAO extends GeneralDAO<Storage> {
    public static final String FIND_ST_BY_ID_STORAGE = "FROM Storage WHERE ID = :ID ";
    public static final String DELETE_ST_BY_ID_STORAGE = "DELETE FROM Storage WHERE ID = :ID";

    public StorageDAO() {
    }

    @Override
    public String setHql() {
        return FIND_ST_BY_ID_STORAGE;
    }

    @Override
    public String setHqlDelEntity() {
        return DELETE_ST_BY_ID_STORAGE;
    }

}
