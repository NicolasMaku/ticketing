package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Avion;
import itu.nicolas.ticketing.models.ConfigurationLimite;
import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ConfigurationLimiteRepository {
    private EntityManager em;

    public ConfigurationLimiteRepository(EntityManager em) {
        this.em = em;
    }

    public List<ConfigurationLimite> findAll() {
        return em.createQuery("SELECT v FROM ConfigurationLimite v", ConfigurationLimite.class).getResultList();
    }
    public ConfigurationLimite findById(int id) {
        try {
            return em.find(ConfigurationLimite.class, id);
        } catch (Exception e) {
            return null;
        }
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
