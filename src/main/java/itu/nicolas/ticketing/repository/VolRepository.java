package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class VolRepository {
    private EntityManager em;

    public VolRepository(EntityManager em) {
        this.em = em;
    }

    public Vol findById(int idVol) {
        return em.find(Vol.class, idVol);
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

    public void update(Vol vol) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vol);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du Vol", ex);
        }
    }

    public void deleteById(Integer id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Vol vol = em.find(Vol.class, id);
            if (vol != null) {
                em.remove(vol);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression du Vol", ex);
        }
    }

    public List<Vol> rechercheMulti(
            Integer idVilleDepart, Integer idVilleArrivee,
            LocalDateTime departVol, LocalDateTime arriveeVol,
            Integer idAvion, Double prixMin, Double prixMax
    ) {
        String sql = "SELECT * FROM vol \n" +
                "        WHERE id_ville_depart = coalesce(cast(?1 as integer), id_ville_depart) \n" +
                "          AND id_ville_arrivee = coalesce(cast(?2 as integer), id_ville_arrivee) \n" +
                "          AND depart_vol >= coalesce(cast(?3 as timestamp), depart_vol) \n" +
                "          AND depart_vol <= coalesce(cast(?4 as timestamp), depart_vol) \n" +
                "          AND id_avion = coalesce(cast(?5 as integer), id_avion)";

        return em.createNativeQuery(sql, Vol.class)
                .setParameter(1, idVilleDepart)
                .setParameter(2, idVilleArrivee)
                .setParameter(3, departVol)
                .setParameter(4, arriveeVol)
                .setParameter(5, idAvion)
                .getResultList();

//        String sql = "SELECT * FROM vol \n" +
//                "        WHERE id_ville_depart = coalesce(?1, id_ville_depart) \n" +
//                "          AND id_ville_arrivee = coalesce(?2, id_ville_arrivee) \n" +
//                "          AND depart_vol >= coalesce(?3, depart_vol) \n" +
//                "          AND depart_vol <= coalesce(?4, depart_vol) \n" +
//                "          AND id_avion = coalesce(?5, id_avion) \n" +
//                "          AND prix >= coalesce(?6, prix) " +
//                "          AND prix <= coalesce(?7, prix)";
//
//        return em.createNativeQuery(sql, Vol.class)
//                .setParameter(1, idVilleDepart)
//                .setParameter(2, idVilleArrivee)
//                .setParameter(3, departVol)
//                .setParameter(4, arriveeVol)
//                .setParameter(5, idAvion)
//                .setParameter(6, prixMin)
//                .setParameter(7, prixMax)
//                .getResultList();
    }

}
