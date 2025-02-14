package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ville", nullable = false)
    private Integer id;

    @Column(name = "nom", length = 50)
    private String nom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}