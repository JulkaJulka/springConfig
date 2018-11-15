package com.lesson3.hwStorageFile.dao;

import com.lesson3.hwStorageFile.exception.BadRequestException;
import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.lesson3.hwStorageFile.dao.StorageDAO.FIND_ST_BY_ID_STORAGE;

@Repository
public class FileDAO extends GeneralDAO<File> {
    public static final String FIND_FL_BY_ID_FILE = "FROM File WHERE ID = :ID ";
    public static final String DELETE_FL_BY_ID_FILE = "DELETE FROM File WHERE ID = :ID";

    static {
        setHql(FIND_FL_BY_ID_FILE);
    }

    static {
        setHqlDelEntity(DELETE_FL_BY_ID_FILE);
    }

    private StorageDAO storageDAO;

    public FileDAO() {
    }

    @Autowired
    public FileDAO(StorageDAO storageDAO) {
        this.storageDAO = storageDAO;
    }

    @Override
    public File findById(long id) {
        return super.findById(id);
    }

    public void delete(Storage storage, File file) throws BadRequestException {

        Transaction tr = null;
        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            tr = session.getTransaction();

            File deleteFile = findById(storage, file.getId());
            Storage storageDel = storageDAO.findById(storage.getId());
            if (deleteFile == null) {
                throw new BadRequestException("File id " + file.getId() + " name " + file.getName() + " doesn't exist in storage id " + storage.getId());
            }
            if (deleteFile.equals(file)) {
                tr.begin();
                deleteFile.setStorage(null);
                update(deleteFile);

                Storage storageFrom = storageDAO.findById(storageDel.getId());

                storageFrom.setStorageSize(storageFrom.getStorageSize() + deleteFile.getSize());
                storageDAO.update(storageFrom);
                tr.commit();
            }

        } catch (HibernateException e) {
            tr.rollback();
            System.err.println(e.getMessage());
            throw new HibernateException("Something went wrong");
        }
    }

    public File findById(Storage storage, long id) {

        try (Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery("SELECT f FROM File f JOIN FETCH f.storage s WHERE s.id =  " + storage.getId() +
                    " and f.id = " + id);

            if (query.uniqueResult() == null)
                return null;

            return (File) query.getSingleResult();

        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            throw new HibernateException("Something went wrong");

        }
    }

    public File put(Storage storage, File file) {

        Transaction tr = null;
        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();
            setHql(FIND_FL_BY_ID_FILE);
            File foundFile = findById(file.getId());
            setHql(FIND_ST_BY_ID_STORAGE);
            Storage foundStorage = storageDAO.findById(storage.getId());

            if (foundFile == null) {
                if (file.getStorage() == null) {
                    file.setStorage(foundStorage);
                    save(file);
                    foundStorage.setStorageSize(foundStorage.getStorageSize() - file.getSize());
                    storageDAO.update(foundStorage);
                    tr.commit();
                    return file;
                }
            }
            if (foundFile.getStorage() != null) {
                tr.commit();
                return null;
            }

            foundFile.setStorage(foundStorage);
            update(foundFile);

            foundStorage.setStorageSize(foundStorage.getStorageSize() - foundFile.getSize());
            storageDAO.update(foundStorage);

            tr.commit();

            return foundFile;


        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
            throw new HibernateException("Save is failed");
        }
    }


    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException {

        Transaction tr = null;
        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            File transferFile = findById(storageFrom, id);
            if (transferFile == null)
                throw new BadRequestException("File with id " + id + " is not found in Storage " + storageFrom.getId());


            transferFile.setStorage(storageTo);
            update(transferFile);

            storageFrom.setStorageSize(storageFrom.getStorageSize() + transferFile.getSize());
            storageDAO.update(storageFrom);

            storageTo.setStorageSize(storageTo.getStorageSize() - transferFile.getSize());
            storageDAO.update(storageTo);

            tr.commit();

            return transferFile;

        } catch (HibernateException e) {
            tr.rollback();
            System.err.println(e.getMessage());
            throw new HibernateException("Something went wrong");
        }
    }

    public List<File> transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException {

        Transaction tr = null;
        try (Session session = GeneralDAO.createSessionFactory().openSession()) {
            tr = session.getTransaction();
            tr.begin();

            Storage storageDBFrom = storageDAO.findById(storageFrom.getId());
            Storage storageDBTo = storageDAO.findById(storageTo.getId());

            Query queryFindFilesByStorage = session.createQuery("SELECT f FROM File f JOIN FETCH f.storage s WHERE s.id =  " + storageFrom.getId());

            List<File> transferFiles = queryFindFilesByStorage.getResultList();

            List<File> filesStorageFrom = new ArrayList<>();

            for (File fl : transferFiles) {

                filesStorageFrom.add(transferFile(storageDBFrom, storageDBTo, fl.getId()));
            }
            tr.commit();
            return filesStorageFrom;

        } catch (HibernateException e) {
            tr.rollback();
            System.err.println(e.getMessage());
            throw new HibernateException("Something went wrong");
        }
    }


}
