package com.lesson3.hwStorageFile.service;

import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.dao.FileDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class FileService extends GeneralService<File> {
    @Autowired
    private FileDAO fileDAO;

    public FileService() {
    }

    @Override
    public File findObjectById(long id) {
        return fileDAO.findById(id);
    }

}
