package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Avion;
import itu.nicolas.ticketing.models.Ville;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AvionRepository {
    private EntityManager em;

    public AvionRepository(EntityManager em) {
        this.em = em;
    }

    public List<Avion> findAll() {
        return em.createQuery("SELECT v FROM Avion v", Avion.class).getResultList();
    }
    public Avion findById(int id) {
        try {
            return em.find(Avion.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
