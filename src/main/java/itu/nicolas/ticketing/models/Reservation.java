package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

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

    public Reservation(LocalDateTime dateReservation, UserTicketing idUserTicketing, OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.dateReservation = dateReservation;
        this.idUserTicketing = idUserTicketing;
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

}