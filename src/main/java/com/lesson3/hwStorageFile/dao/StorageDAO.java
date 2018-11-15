package com.lesson3.hwStorageFile.dao;

import com.lesson3.hwStorageFile.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StorageDAO extends GeneralDAO<Storage> {
    public static final String FIND_ST_BY_ID_STORAGE = "FROM Storage WHERE ID = :ID ";
    public static final String DELETE_ST_BY_ID_STORAGE = "DELETE FROM Storage WHERE ID = :ID";

    static {
        setHql(FIND_ST_BY_ID_STORAGE);
    }

    static {
        setHqlDelEntity(DELETE_ST_BY_ID_STORAGE);
    }

    public StorageDAO() {
    }

    @Override
    public Storage findById(long id) {
        setHql(FIND_ST_BY_ID_STORAGE);
        return super.findById(id);
    }
}
