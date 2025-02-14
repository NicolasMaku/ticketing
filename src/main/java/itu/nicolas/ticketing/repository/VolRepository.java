package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class VolRepository {
    private EntityManager em;

    public VolRepository(EntityManager em) {
        this.em = em;
    }

    public List<Vol> findAll() {
        return em.createQuery("SELECT v FROM Vol v", Vol.class).getResultList();
    }

    public void save(Vol vol) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(vol);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du Vol", ex);
        }
    }
}
