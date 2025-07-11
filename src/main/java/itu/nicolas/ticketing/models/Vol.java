package itu.nicolas.ticketing.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vol")
public class Vol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vol", nullable = false)
    private Integer id;

    @Column(name = "depart_vol")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departVol;

    @Column(name = "arrivee_vol")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arriveeVol;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ville_depart", nullable = false)
    private Ville idVilleDepart;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ville_arrivee", nullable = false)
    private Ville idVilleArrivee;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_avion", nullable = false)
    private Avion idAvion;

    @JsonIgnore
    @OneToMany(mappedBy = "idVol", cascade = CascadeType.REMOVE)
    private List<OffreSiegeAvionVol> offreSiegeAvionVols = new ArrayList<>();

    @Transient
    @JsonIgnore
    private String departVolString;

    @Transient
    @JsonIgnore
    private String arriveeVolString;

    @JsonProperty("villeDepart")
    public String getVilleDepartNom() {
        return idVilleDepart != null ? idVilleDepart.getNom() : "";
    }

    @JsonProperty("villeArrivee")
    public String getVilleArriveeNom() {
        return idVilleArrivee != null ? idVilleArrivee.getNom() : "";
    }

    @JsonProperty("Avion")
    public String getAvionNom() {
        return idAvion != null ? idAvion.getLibelle() : "";
    }

    public List<OffreSiegeAvionVol> getOffreSiegeAvionVols() {
        return offreSiegeAvionVols;
    }

    public void setOffreSiegeAvionVols(List<OffreSiegeAvionVol> offreSiegeAvionVols) {
        this.offreSiegeAvionVols = offreSiegeAvionVols;
    }

    public Avion getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

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

    public String getDepartVolString() {
        return departVolString;
    }

    public void setDepartVolString(String departVolString) {
        this.departVolString = departVolString;
    }

    public String getArriveeVolString() {
        return arriveeVolString;
    }

    public void setArriveeVolString(String arriveeVolString) {
        this.arriveeVolString = arriveeVolString;
    }

    public Vol() {
    }

    public Vol(LocalDateTime departVol, LocalDateTime arriveeVol, Ville idVilleDepart, Ville idVilleArrivee, Avion idAvion) {
        this.departVol = departVol;
        this.arriveeVol = arriveeVol;
        this.idVilleDepart = idVilleDepart;
        this.idVilleArrivee = idVilleArrivee;
        this.idAvion = idAvion;
    }

    public void adapt(Vol v) {
        this.id = v.getId();
        this.idAvion = v.getIdAvion();
        this.departVol = v.departVol;
        this.arriveeVol = v.arriveeVol;
        this.idVilleDepart = v.idVilleDepart;
        this.idVilleArrivee = v.idVilleArrivee;
    }
    public void findById(int idVol, EntityManager em) {
        Vol v = em.find(Vol.class, idVol);

        if(v != null) this.adapt(v);
    }

    public List<Vol> findAll(EntityManager em) {
        return em.createQuery("SELECT v FROM Vol v", Vol.class).getResultList();
    }
}