package com.lesson6;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DAO {

    private static String FIND_BY_ID_ITEM = "FROM Item WHERE ID =:id";

    @PersistenceContext
    private EntityManager entityManager;

    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item delete(Long id) throws BadRequestException {

        if (entityManager.find(Item.class, id) == null)
            throw new BadRequestException("Item id " + id + " doesn't exist in DB");

        Item deleteItem = findById(id);
        entityManager.remove(deleteItem);

        return deleteItem;
    }

    public Item update(Item item) throws BadRequestException {
        if (entityManager.find(Item.class, item.getId()) == null)
            throw new BadRequestException("Item id " + item.getId() + " doesn't exist in DB");

        entityManager.merge(item);
        return item;
    }

    public Item findById(long id) {
        entityManager.createQuery(FIND_BY_ID_ITEM);

        Query query = entityManager.createQuery(FIND_BY_ID_ITEM);
        query.setParameter("id", id);

        Item item = (Item) query.getSingleResult();

        if (item == null)
            return null;
        return item;

    }
}
