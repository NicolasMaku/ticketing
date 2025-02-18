package itu.nicolas.ticketing.models;

import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_ticketing")
public class UserTicketing {
    @Id
    @Column(name = "id_user_ticketing", nullable = false)
    private Integer id;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password", length = 50)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reservation> findAllReservationEnCours() {
        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return findAllReservationEnCours(em);
        }
    }

    public List<Reservation> findAllReservationEnCours(EntityManager em) {
        return em.createQuery("SELECT v FROM Reservation v where v.idUserTicketing.id = ?1 and v.idOffreSiegeAvionVol.idVol.departVol > CURRENT_TIMESTAMP", Reservation.class)
                .setParameter(1, this.getId())
                .getResultList();
    }

    public List<Reservation> findAllReservationFini() {
        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return findAllReservationFini(em);
        }
    }

    public List<Reservation> findAllReservationFini(EntityManager em) {
        return em.createQuery("SELECT v FROM Reservation v where v.idUserTicketing.id = ?1 and v.idOffreSiegeAvionVol.idVol.departVol < CURRENT_TIMESTAMP", Reservation.class)
                .setParameter(1, this.getId())
                .getResultList();
    }


    public void findById(int id, EntityManager em) {
        try {
            UserTicketing u = em.find(UserTicketing.class, id);
            this.adapt(u);
        } catch (Exception e) {
            return;
        }
    }

    public void adapt(UserTicketing u) {
        this.id = u.getId();
        this.password = u.getPassword();
        this.username = u.username;
    }


}