package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "avion")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avion", nullable = false)
    private Integer id;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "date_fabrication")
    private LocalDate dateFabrication;

    @OneToMany(mappedBy = "idAvion")
    private List<SiegeAvion> siegeAvions = new ArrayList<>();

    public List<SiegeAvion> getSiegeAvions() {
        return siegeAvions;
    }

    public void setSiegeAvions(List<SiegeAvion> siegeAvions) {
        this.siegeAvions = siegeAvions;
    }

    public LocalDate getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //TODO [JPA Buddy] generate columns from DB
}