package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "config_prix")
public class ConfigPrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_config_prix", nullable = false)
    private Integer id;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "reduction")
    private Double reduction;

    public Double getReduction() {
        return reduction;
    }

    public void setReduction(Double reduction) {
        this.reduction = reduction;
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

    public void adapt(ConfigPrix cp) {
        this.setLibelle(cp.getLibelle());
        this.setReduction(cp.getReduction());
    }

    public void findById(EntityManager em) {
        try {
            ConfigPrix cp = em.find(ConfigPrix.class, id);
            this.adapt(cp);
        } catch (Exception e) {
            return ;
        }
    }

    public void findPrixEnfant(EntityManager em) {
        try {
            ConfigPrix cp = em.find(ConfigPrix.class, 1);
            this.adapt(cp);
        } catch (Exception e) {
            return ;
        }
    }
}