package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Avion;
import itu.nicolas.ticketing.models.OffreSiegeAvionVol;
import itu.nicolas.ticketing.models.SiegeAvion;
import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class OffreSiegeAvionVolRepository {
    private EntityManager em;

    public OffreSiegeAvionVolRepository(EntityManager em) {
        this.em = em;
    }

    public List<OffreSiegeAvionVol> findAll() {
        return em.createQuery("SELECT v FROM OffreSiegeAvionVol v", OffreSiegeAvionVol.class).getResultList();
    }
    public OffreSiegeAvionVol findById(int id) {
        try {
            return em.find(OffreSiegeAvionVol.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<OffreSiegeAvionVol> findByVol(Vol vol) {
        return em.createQuery("SELECT s FROM OffreSiegeAvionVol s WHERE s.idVol = :vol", OffreSiegeAvionVol.class)
                .setParameter("vol", vol)
                .getResultList();
    }

    public void saveAll(List<OffreSiegeAvionVol> offres) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (OffreSiegeAvionVol sa : offres) {
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

    public void update(OffreSiegeAvionVol offre) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(offre);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour de l'offre", ex);
        }
    }

    public List<OffreSiegeAvionVol> findEtat() {
        String sql = "";

        return em.createNativeQuery(sql, OffreSiegeAvionVol.class)
                .getResultList();
    }
}
