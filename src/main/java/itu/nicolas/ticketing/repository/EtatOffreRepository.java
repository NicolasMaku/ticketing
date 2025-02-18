package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.EtatOffre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EtatOffreRepository {
    private EntityManager em;

    public EtatOffreRepository(EntityManager em) {
        this.em = em;
    }

    public List<EtatOffre> findAll() {
        return em.createQuery("SELECT v FROM EtatOffre v", EtatOffre.class).getResultList();
    }
//    public EtatOffre findById(int id) {
//        try {
//            return em.find(EtatOffre.class, id);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public EtatOffre findByIdOffre(int idOffre) {
        try {
            List<EtatOffre> etatOffres = em.createQuery("SELECT v FROM EtatOffre v where v.idOffreSiegeAvionVol.id= ?1", EtatOffre.class)
                    .setParameter(1, idOffre)
                    .getResultList();

            if (!etatOffres.isEmpty())
                return etatOffres.get(0);

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void save(EtatOffre etatOffre) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(etatOffre);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du Vol", ex);
        }
    }

    public void update(EtatOffre etatOffre) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(etatOffre);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du Vol", ex);
        }
    }
}
