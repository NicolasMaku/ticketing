package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.OffreSiegeAvionVolPromotion;
import itu.nicolas.ticketing.models.OffreSiegeAvionVolPromotion;
import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class OffreSiegeAvionVolPromotionRepository {
    private EntityManager em;

    public OffreSiegeAvionVolPromotionRepository(EntityManager em) {
        this.em = em;
    }

    public List<OffreSiegeAvionVolPromotion> findAll() {
        return em.createQuery("SELECT v FROM OffreSiegeAvionVolPromotion v", OffreSiegeAvionVolPromotion.class).getResultList();
    }

    public List<OffreSiegeAvionVolPromotion> findByVol(Vol vol) {
        return em.createQuery("SELECT s FROM OffreSiegeAvionVolPromotion s WHERE s.idVol = :vol", OffreSiegeAvionVolPromotion.class)
                .setParameter("vol", vol.getId())
                .getResultList();
    }

    public void saveAll(List<OffreSiegeAvionVolPromotion> offres) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (OffreSiegeAvionVolPromotion sa : offres) {
                em.persist(sa);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du offre", ex);
        }
    }

    public void update(OffreSiegeAvionVolPromotion offre) {
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

}
