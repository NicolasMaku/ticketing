package itu.nicolas.ticketing.models;

import itu.nicolas.ticketing.repository.UserTicketingRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.*;
import mg.itu.prom16.annotations.verification.Numeric;
import mg.itu.prom16.annotations.verification.Required;
import mg.itu.prom16.annotations.verification.Size;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_role", nullable = false)
    private Role idRole;

    @Column(name = "email")
    @Size(min = 12,max= 18)
    @Required
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getIdRole() {
        return idRole;
    }

    public void setIdRole(Role idRole) {
        this.idRole = idRole;
    }

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
        List<Reservation> list = em.createQuery("SELECT v FROM Reservation v where v.idUserTicketing.id = ?1 and v.idVol.departVol > CURRENT_TIMESTAMP", Reservation.class)
                .setParameter(1, this.getId())
                .getResultList();

        for (Reservation r : list)
            r.getReservationFilles(em);

        return list;
    }

    public List<Reservation> findAllReservationFini() {
        try(EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            return findAllReservationFini(em);
        }
    }

    public List<Reservation> findAllReservationFini(EntityManager em) {
        List<Reservation> list = em.createQuery("SELECT v FROM Reservation v where v.idUserTicketing.id = ?1 and v.idVol.departVol < CURRENT_TIMESTAMP", Reservation.class)
                .setParameter(1, this.getId())
                .getResultList();

        for (Reservation r : list)
            r.getReservationFilles(em);

        return list;
    }

    public UserTicketing() {
    }

    public UserTicketing(String username, String password, Role idRole, String email) {
        this.username = username;
        this.password = password;
        this.idRole = idRole;
        this.email = email;
    }

    public UserTicketing(String email, String password) {
        this.password = password;
        this.email = email;
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
        this.idRole = u.getIdRole();
    }

    public boolean findByLogin(EntityManager em) {
        List<UserTicketing> users = em.createQuery("SELECT v FROM UserTicketing v where v.email = ?1 and v.password = ?2", UserTicketing.class)
                .setParameter(1, email)
                .setParameter(2, password)
                .getResultList();

        if (users.isEmpty()) return false;
        if (users.size() > 1) return false;

        this.adapt(users.get(0));
        return true;
    }


}