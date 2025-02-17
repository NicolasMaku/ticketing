package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

/**
 * Mapping for DB view
 */
@Entity
@Table(name = "etat_offre")
public class EtatOffre {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_offre_siege_avion_vol")
    private OffreSiegeAvionVol idOffreSiegeAvionVol;

    @Column(name = "nombre")
    private Integer nombre;

    public OffreSiegeAvionVol getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }

    public void setIdOffreSiegeAvionVol(OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    protected EtatOffre() {
    }
}