package com.lesson3.hwStorageFile.service;

import com.lesson3.hwStorageFile.exception.BadRequestException;
import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.model.Storage;
import com.lesson3.hwStorageFile.dao.FileDAO;
import com.lesson3.hwStorageFile.dao.GeneralDAO;
import com.lesson3.hwStorageFile.dao.StorageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GeneralService<T> {

    private StorageDAO storageDAO;
    private FileDAO fileDAO;
    private GeneralDAO generalDAO;


    public GeneralService() {
    }

    @Autowired
    public GeneralService(GeneralDAO generalDAO) {
        this.generalDAO = generalDAO;

    }

    public T findObjectById(long id) {

        T t = (T) generalDAO.findById(id);
        return t;
    }

    public File findById(Storage storage, Long id) {
        if (storage.getFiles() == null)
            return null;

        for (int i = 0; i < storage.getFiles().size(); i++) {
            for (File file : storage.getFiles()) {
                if (file != null && id.equals(file.getId())) {
                    return file;
                }
            }
        }
        return null;

    }

    public boolean checkLimitation(Storage storage, File file) throws BadRequestException {

        File putFile = findById(storage, file.getId());

        if (putFile != null)
            throw new BadRequestException("File with Id " + file.getId() +
                    " already exists in storage " + storage.getId() + " Storage can't save files with the same ID. ");
        if (!checkLengthFileName(file.getName()))
            throw new BadRequestException("Name of file id " + file.getId() + " is wrong");
        if (!checkFormatsSupported(storage, file))
            throw new BadRequestException("Format " + file.getFormat() + " is not supported by storage " + storage.getId());
        if (!checkSizeFileWithSizeStorage(storage, file))
            throw new BadRequestException("Not enough space in storage with Id " + storage.getId());
        return true;
    }

    public boolean checkSizeFileWithSizeStorage(Storage storage, File file) {
        if (file.getSize() > storage.getStorageSize())
            return false;
        return true;

    }

    public static boolean checkLengthFileName(String word) {

        if (word.isEmpty())
            return false;
        int limitLengthOfNameFile = 10;

        if (word.length() > limitLengthOfNameFile)
            return false;
        return true;
    }

    public boolean checkLimitsFiles(Storage storageTo, List<File> files) throws BadRequestException {
        checkFormatsFiles(storageTo, files);
        if (storageTo.getStorageSize() < files.size())
            throw new BadRequestException("Size of files exceed the empty size of storageTo " + storageTo.getId());
        return true;
    }

    public boolean checkFormatsFiles(Storage storageTo, List<File> files) throws BadRequestException {
        String str = storageTo.getFormatsSupported();
        String[] formats = str.split(",");
        String wrongFormat = "";
        int index = 0;

        for (File fl : files) {
            wrongFormat = fl.getFormat();
            for (String el : formats) {
                if (fl.getFormat().equals(el)) {
                    index++;
                    break;
                }
            }
        }
        if (index != files.size())
            throw new BadRequestException("Storage id " + storageTo.getId() + " is not supported format " + wrongFormat);

        return true;
    }

    public boolean checkFormatsSupported(Storage storage, File file) {

        String[] arFormats = storage.getFormatsSupported().split(",");
        for (String el : arFormats) {
            if (el.equals(file.getFormat()))
                return true;
        }
        return false;
    }
}
