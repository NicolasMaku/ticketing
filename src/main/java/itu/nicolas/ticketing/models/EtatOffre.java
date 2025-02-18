package itu.nicolas.ticketing.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Synchronize;
import org.hibernate.annotations.Cache;

/**
 * Mapping for DB view
 */
@Entity
@Immutable
@Table(name = "etat_offre")
@Cacheable(value = false)
public class EtatOffre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_offre_siege_avion_vol", nullable = false)
    private OffreSiegeAvionVol idOffreSiegeAvionVol;

    @Column(name = "nombre")
    private Integer nombre;

    public Long getId() {
        return id;
    }

    public OffreSiegeAvionVol getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }

    public Integer getNombre() {
        return nombre;
    }

    protected EtatOffre() {
    }
}