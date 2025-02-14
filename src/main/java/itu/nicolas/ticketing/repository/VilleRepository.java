package itu.nicolas.ticketing.repository;

import itu.nicolas.ticketing.models.Ville;
import itu.nicolas.ticketing.models.Vol;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VilleRepository {
    private EntityManager em;

    public VilleRepository(EntityManager em) {
        this.em = em;
    }

    public List<Ville> findAll() {
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }
    public Ville findById(int id) {
        try {
            return em.find(Ville.class, id);
        } catch (Exception e) {
            return null;
        }
    }
}
