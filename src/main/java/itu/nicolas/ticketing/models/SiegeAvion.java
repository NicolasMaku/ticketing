package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

@Entity
@Table(name = "siege_avion")
public class SiegeAvion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_siege_avion", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private Integer nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_avion", nullable = false)
    private Avion idAvion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type_siege", nullable = false)
    private TypeSiege idTypeSiege;

    public TypeSiege getIdTypeSiege() {
        return idTypeSiege;
    }

    public void setIdTypeSiege(TypeSiege idTypeSiege) {
        this.idTypeSiege = idTypeSiege;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Avion getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

}