package com.lesson3.hwStorageFile.service;

import com.lesson3.hwStorageFile.model.Storage;
import com.lesson3.hwStorageFile.dao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class StorageService extends GeneralService<Storage> {

    @Autowired
    private StorageDAO storageDAO;
    public StorageService() {
    }

    public Storage findObjectById(long id)  {

        return storageDAO.findById( id);
    }
}
