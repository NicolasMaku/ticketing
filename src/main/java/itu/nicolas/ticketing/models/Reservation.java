package itu.nicolas.ticketing.models;

import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.*;
import util.MyFile;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation", nullable = false)
    private Integer id;

    @Column(name = "date_reservation")
    private LocalDateTime dateReservation;

    @Column(name = "date_annulation")
    private LocalDateTime dateAnnulation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user_ticketing", nullable = false)
    private UserTicketing idUserTicketing;

    @Transient
    private MyFile file;
    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "idReservationMere")
    private List<ReservationFille> reservationFilles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vol")
    private Vol idVol;

    public Vol getIdVol() {
        return idVol;
    }

    public void setIdVol(Vol idVol) {
        this.idVol = idVol;
    }

//    public List<ReservationFille> getReservationFilles() {
//        return reservationFilles;
//    }
//
//    public void setReservationFilles(List<ReservationFille> reservationFilles) {
//        this.reservationFilles = reservationFilles;
//    }

    public List<ReservationFille> getReservationFilles(EntityManager em) {
        if (this.reservationFilles == null || this.reservationFilles.isEmpty()) {
            Reservation reservation = new Reservation();
            ReservationFille rf = new ReservationFille();
            rf.setIdReservationMere(this);

            this.setReservationFilles(rf.findAllByIdMere(em));
        }

        return this.reservationFilles;
    }

    public List<ReservationFille> getReservationFilles() {
        if (this.reservationFilles == null || this.reservationFilles.isEmpty()) {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            Reservation reservation = new Reservation();
            ReservationFille rf = new ReservationFille();
            rf.setIdReservationMere(this);

            this.setReservationFilles(rf.findAllByIdMere(em));
        }

        return this.reservationFilles;
    }

    public void setReservationFilles(List<ReservationFille> reservationFilles) {
        this.reservationFilles = reservationFilles;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public UserTicketing getIdUserTicketing() {
        return idUserTicketing;
    }

    public void setIdUserTicketing(UserTicketing idUserTicketing) {
        this.idUserTicketing = idUserTicketing;
    }

    public Reservation() {
    }

    public Reservation(LocalDateTime dateReservation, UserTicketing idUserTicketing) {
        this.dateReservation = dateReservation;
        this.idUserTicketing = idUserTicketing;
    }

    public List<Reservation> findAll(EntityManager em) {
        List<Reservation> list = em.createQuery("SELECT v FROM Reservation v", Reservation.class).getResultList();
        for (Reservation r : list)
            r.getReservationFilles();

        return list;
    }

    public List<Reservation> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return findAll(em);
    }

    public void findById(int id, EntityManager em) {
        try {
            Reservation res = em.find(Reservation.class, id);
            res.getReservationFilles(em);
            this.adapt(res);
        } catch (Exception e) {
            return ;
        }
    }

    public void adapt(Reservation res) {
        this.id = res.getId();
        this.idUserTicketing = res.getIdUserTicketing();
        this.dateReservation = res.getDateReservation();
        this.dateAnnulation = res.getDateAnnulation();
    }

    public void save(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(this);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du reservation", ex);
        }
    }

    public void update(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(this);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du reservation", ex);
        }
    }
}