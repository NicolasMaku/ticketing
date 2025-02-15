package itu.nicolas.ticketing.repository;

import jakarta.persistence.EntityManager;
import java.util.List;

public class GenericRepository<T> {
    private final EntityManager em;
    private final Class<T> entityClass;

    public GenericRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public T findById(int id) {
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            return null;
        }
    }
}
