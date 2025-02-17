package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "configuration_limite")
public class ConfigurationLimite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuration", nullable = false)
    private Integer id;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "nbre_heure")
    private Integer nbreHeure;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNbreHeure() {
        return nbreHeure;
    }

    public void setNbreHeure(Integer nbreHeure) {
        this.nbreHeure = nbreHeure;
    }

    public void findByLibelle(String libelle, EntityManager em) {
        List<ConfigurationLimite> confs = em.createQuery("SELECT v FROM ConfigurationLimite v where v.libelle= ?1", ConfigurationLimite.class)
                .setParameter(1, libelle)
                .getResultList();

        ConfigurationLimite search = null;
        if (!confs.isEmpty())
            search = confs.get(0);

        adapt(search);
    }

    public void adapt(ConfigurationLimite c) {
        this.id = c.getId();
        this.libelle = c.getLibelle();
        this.nbreHeure = c.getNbreHeure();
    }
}