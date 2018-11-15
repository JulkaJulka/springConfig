package com.lesson3.hwStorageFile.service;

import com.lesson3.hwStorageFile.dao.StorageDAO;
import com.lesson3.hwStorageFile.exception.BadRequestException;
import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.dao.FileDAO;
import com.lesson3.hwStorageFile.model.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService extends GeneralService<File> {

    private FileDAO fileDAO;
    private  StorageDAO storageDAO;


    @Autowired
    public FileService(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
        this.storageDAO = storageDAO;
    }

    @Override
    public File findObjectById(long id) {
        return fileDAO.findById(id);
    }

    public void delete(Storage storage, File file) throws BadRequestException {
        fileDAO.delete(storage, file);
    }

    public File put(Storage storage, File file) throws BadRequestException {
        checkLimitation(storage, file);
        return fileDAO.put(storage, file);

    }
    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException {
        if (findById(storageFrom, id) == null)
            throw new BadRequestException("File id " + id + "doesn't exist in Storage id " + storageFrom.getId());
        checkLimitation(storageTo, findById(storageFrom, id));
        return fileDAO.transferFile(storageFrom, storageTo, id);
    }
    public List<File> transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException {
        checkLimitsFiles(storageTo, storageFrom.getFiles());
        return fileDAO.transferAll(storageFrom, storageTo);
    }
}
