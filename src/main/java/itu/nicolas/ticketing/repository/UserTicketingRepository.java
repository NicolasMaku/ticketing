package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.ConfigurationLimite;
import itu.nicolas.ticketing.models.UserTicketing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class UserTicketingRepository {
    private EntityManager em;

    public UserTicketingRepository(EntityManager em) {
        this.em = em;
    }

    public List<UserTicketing> findAll() {
        return em.createQuery("SELECT v FROM UserTicketing v", UserTicketing.class).getResultList();
    }

    public void save(ConfigurationLimite conf) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(conf);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du Vol", ex);
        }
    }

    public void update(ConfigurationLimite conf) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conf);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du Vol", ex);
        }
    }
}
