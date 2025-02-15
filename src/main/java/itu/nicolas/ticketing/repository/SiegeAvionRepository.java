package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Avion;
import itu.nicolas.ticketing.models.SiegeAvion;
import itu.nicolas.ticketing.models.Ville;
import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class SiegeAvionRepository {
    private EntityManager em;

    public SiegeAvionRepository(EntityManager em) {
        this.em = em;
    }

    public List<SiegeAvion> findAll() {
        return em.createQuery("SELECT v FROM SiegeAvion v", SiegeAvion.class).getResultList();
    }
    public SiegeAvion findById(int id) {
        try {
            return em.find(SiegeAvion.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SiegeAvion> findByAvion(Avion avion) {
        return em.createQuery("SELECT s FROM SiegeAvion s WHERE s.idAvion = :avion", SiegeAvion.class)
                .setParameter("avion", avion)
                .getResultList();
    }

    public void saveAll(List<SiegeAvion> siegeAvions) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (SiegeAvion sa : siegeAvions) {
                em.persist(sa);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du Vol", ex);
        }
    }
}
