package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "vol")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vol", nullable = false)
    private Integer id;

    @Column(name = "depart_vol")
    private LocalDateTime departVol;

    @Column(name = "arrivee_vol")
    private LocalDateTime arriveeVol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ville_depart", nullable = false)
    private Ville idVilleDepart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ville_arrivee", nullable = false)
    private Ville idVilleArrivee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDepartVol() {
        return departVol;
    }

    public void setDepartVol(LocalDateTime departVol) {
        this.departVol = departVol;
    }

    public LocalDateTime getArriveeVol() {
        return arriveeVol;
    }

    public void setArriveeVol(LocalDateTime arriveeVol) {
        this.arriveeVol = arriveeVol;
    }

    public Ville getIdVilleDepart() {
        return idVilleDepart;
    }

    public void setIdVilleDepart(Ville idVilleDepart) {
        this.idVilleDepart = idVilleDepart;
    }

    public Ville getIdVilleArrivee() {
        return idVilleArrivee;
    }

    public void setIdVilleArrivee(Ville idVilleArrivee) {
        this.idVilleArrivee = idVilleArrivee;
    }

    public Vol() {
    }

    public Vol(LocalDateTime departVol, LocalDateTime arriveeVol, Ville idVilleDepart, Ville idVilleArrivee) {
        this.departVol = departVol;
        this.arriveeVol = arriveeVol;
        this.idVilleDepart = idVilleDepart;
        this.idVilleArrivee = idVilleArrivee;
    }
}