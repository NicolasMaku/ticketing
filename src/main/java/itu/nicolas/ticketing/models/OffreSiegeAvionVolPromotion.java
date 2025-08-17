package itu.nicolas.ticketing.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Entity
@Immutable
@Table(name = "offre_siege_avion_vol_promotion")
public class OffreSiegeAvionVolPromotion {
    @Id
    @Column(name = "id_offre_siege_avion_vol")
    private Integer idOffreSiegeAvionVol;

    @Column(name = "prix_normal")
    private Double prixNormal;

    @Column(name = "reduction")
    private Double reduction;

    @Column(name = "prom_restant")
    private Long promRestant;

    @Column(name = "billet_restant")
    private Long billetRestant;

    @Column(name = "prix_reel")
    private Double prixReel;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "id_vol")
    private Integer idVol;

    public Integer getIdVol() {
        return idVol;
    }

    public Integer getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }


    public Long getPromRestant() {
        return promRestant;
    }

    public Long getBilletRestant() {
        return billetRestant;
    }

    public Double getPrixNormal() {
        return prixNormal;
    }

    public Double getReduction() {
        return reduction;
    }

    public Double getPrixReel() {
        return prixReel;
    }

    public String getLibelle() {
        return libelle;
    }

    protected OffreSiegeAvionVolPromotion() {
    }
}