package itu.nicolas.ticketing.models;

import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.*;
import util.MyFile;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_offre_siege_avion_vol", nullable = false)
    private OffreSiegeAvionVol idOffreSiegeAvionVol;

    @Column(name = "prix")
    private Double prix;

    @Transient
    private MyFile file;

    @Column(name = "passeport")
    private byte[] passeport;

    public byte[] getPasseport() {
        return passeport;
    }

    public void setPasseport(byte[] passeport) {
        this.passeport = passeport;
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

    public OffreSiegeAvionVol getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }

    public void setIdOffreSiegeAvionVol(OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

    public Reservation() {
    }

    public Reservation(LocalDateTime dateReservation, UserTicketing idUserTicketing, OffreSiegeAvionVol idOffreSiegeAvionVol, Double prix) {
        this.dateReservation = dateReservation;
        this.idUserTicketing = idUserTicketing;
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
        this.prix = prix;
    }

    public List<Reservation> findAll(EntityManager em) {
        return em.createQuery("SELECT v FROM Reservation v", Reservation.class).getResultList();
    }

    public void findById(int id, EntityManager em) {
        try {
            Reservation res = em.find(Reservation.class, id);
            this.adapt(res);
        } catch (Exception e) {
            return ;
        }
    }

    public void adapt(Reservation res) {
        this.id = res.getId();
        this.idUserTicketing = res.getIdUserTicketing();
        this.idOffreSiegeAvionVol = res.getIdOffreSiegeAvionVol();
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