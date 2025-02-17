package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.ConfigurationLimite;
import itu.nicolas.ticketing.models.Reservation;
import itu.nicolas.ticketing.models.UserTicketing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ReservationRepository {
    private EntityManager em;

    public ReservationRepository(EntityManager em) {
        this.em = em;
    }

    public List<Reservation> findAll() {
        return em.createQuery("SELECT v FROM Reservation v", Reservation.class).getResultList();
    }
    public Reservation findById(int id) {
        try {
            return em.find(Reservation.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Reservation res) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(res);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du Vol", ex);
        }
    }

    public void update(Reservation res) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(res);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du Vol", ex);
        }
    }
}
