package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "reservation_fille")
public class ReservationFille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation_fille", nullable = false)
    private Integer id;

    @Column(name = "prix", precision = 15, scale = 2)
    private Double prix;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_offre_siege_avion_vol", nullable = false)
    private OffreSiegeAvionVol idOffreSiegeAvionVol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reservation_mere", nullable = false)
    private Reservation idReservationMere;

    @Column(name = "image", length = 50)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_config_prix")
    private ConfigPrix idConfigPrix;

    public ReservationFille(Double prix, OffreSiegeAvionVol idOffreSiegeAvionVol, Reservation idReservationMere, ConfigPrix idConfigPrix) {
        this.prix = prix;
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
        this.idReservationMere = idReservationMere;
        this.idConfigPrix = idConfigPrix;
    }

    public ReservationFille() {

    }

    public void saveAll(List<ReservationFille> resFilles, EntityManager em) {
        if (resFilles.isEmpty()) return;

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (ReservationFille r : resFilles) {
                em.persist(r);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du promotion", ex);
        }
    }

    public List<ReservationFille> findAllByIdMere(EntityManager em) {
        return em.createQuery("SELECT v FROM ReservationFille v WHERE v.idReservationMere.id = :idMere", ReservationFille.class)
                .setParameter("idMere", this.getIdReservationMere().getId())
                .getResultList();
    }

    public ConfigPrix getIdConfigPrix() {
        return idConfigPrix;
    }

    public void setIdConfigPrix(ConfigPrix idConfigPrix) {
        this.idConfigPrix = idConfigPrix;
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public OffreSiegeAvionVol getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }

    public void setIdOffreSiegeAvionVol(OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

    public Reservation getIdReservationMere() {
        return idReservationMere;
    }

    public void setIdReservationMere(Reservation idReservationMere) {
        this.idReservationMere = idReservationMere;
    }


}