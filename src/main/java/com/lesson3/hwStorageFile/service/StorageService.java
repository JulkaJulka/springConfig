package com.lesson3.hwStorageFile.service;

import com.lesson3.hwStorageFile.dao.FileDAO;
import com.lesson3.hwStorageFile.model.Storage;
import com.lesson3.hwStorageFile.dao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService extends GeneralService<Storage> {

    private StorageDAO storageDAO;

    @Autowired
    public StorageService(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
   }

    @Override
    public Storage findObjectById(long id) {

        return storageDAO.findById(id);
    }

}
