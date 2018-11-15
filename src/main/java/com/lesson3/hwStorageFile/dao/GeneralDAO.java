package com.lesson3.hwStorageFile.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public  class GeneralDAO<T> {
    private static SessionFactory sessionFactory;
    private static String hql = "";
    private static String hqlDelEntity = "";
    private FileDAO fileDAO;
    private StorageDAO storageDAO;

    public static void setHql(String hql) {

        GeneralDAO.hql = hql;
    }
    public static void setHqlDelEntity(String hqlDelEntity) {

        GeneralDAO.hqlDelEntity = hqlDelEntity;
    }

    public GeneralDAO() {
    }

    @Autowired
    public GeneralDAO(FileDAO fileDAO,StorageDAO storageDAO) {
        this.fileDAO = fileDAO;
        this.storageDAO = storageDAO;
    }

    public void delete(long id) {
        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            Query queryDelHt = session.createQuery(hqlDelEntity);
            queryDelHt.setParameter("ID", id);
            queryDelHt.executeUpdate();

            tr.commit();

        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
            throw new HibernateException("Delete is failed");
        }
    }

    public T save(T t) throws HibernateException {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            session.save(t);

            tr.commit();
            return t;

        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
            throw new HibernateException("Save is failed");
        }
    }

    public T update(T t) throws HibernateException {

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()) {

            tr = session.getTransaction();
            tr.begin();

            session.update(t);

            tr.commit();
            return t;

        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
            throw new HibernateException("Update is failed");
        }
    }

    public T findById(long id) {
        try (Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery(hql);
            query.setParameter("ID", id);

            query.getResultList();

            if (query.uniqueResult() == null)
                return null;


            return (T) query.getSingleResult();

        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            throw new HibernateException("Something went wrong");
        }
    }

    public static SessionFactory createSessionFactory() {

        //singleton pattern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
