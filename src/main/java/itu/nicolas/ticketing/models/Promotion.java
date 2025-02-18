package itu.nicolas.ticketing.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promotion", nullable = false)
    private Integer id;

    @Column(name = "valeur_pourcentage")
    private Double valeurPourcentage;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_offre_siege_avion_vol", nullable = false)
    private OffreSiegeAvionVol idOffreSiegeAvionVol;

    @Column(name = "nombre_siege")
    private Integer nombreSiege;

    public Integer getNombreSiege() {
        return nombreSiege;
    }

    public void setNombreSiege(Integer nombreSiege) {
        this.nombreSiege = nombreSiege;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValeurPourcentage() {
        return valeurPourcentage;
    }

    public void setValeurPourcentage(Double valeurPourcentage) {
        this.valeurPourcentage = valeurPourcentage;
    }

    public OffreSiegeAvionVol getIdOffreSiegeAvionVol() {
        return idOffreSiegeAvionVol;
    }

    public void setIdOffreSiegeAvionVol(OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

    public Promotion() {
    }

    public void adapt(Promotion p) {
        this.id = p.id;
        this.idOffreSiegeAvionVol = p.idOffreSiegeAvionVol;
        this.nombreSiege = p.nombreSiege;
        this.valeurPourcentage = p.valeurPourcentage;
    }

    public Promotion(Double valeurPourcentage, int nombreSiege, OffreSiegeAvionVol idOffreSiegeAvionVol) {
        this.valeurPourcentage = valeurPourcentage;
        this.nombreSiege = nombreSiege;
        this.idOffreSiegeAvionVol = idOffreSiegeAvionVol;
    }

    public void saveAll(List<Promotion> proms, EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Promotion p : proms) {
                em.persist(p);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du promotion", ex);
        }
    }

    public void findById(int id, EntityManager em) {
        try {
            Promotion p = em.find(Promotion.class, id);
            this.adapt(p);
        } catch (Exception e) {
            return ;
        }
    }

    public void findByIdOffre(int idOffre, EntityManager em) {
        try {
            List<Promotion> promotions = em.createQuery("SELECT v FROM Promotion v where v.idOffreSiegeAvionVol.id= ?1", Promotion.class)
                    .setParameter(1, idOffre)
                    .getResultList();
            Promotion p = new Promotion();
            if (!promotions.isEmpty())
                p = promotions.get(0);

            this.adapt(p);
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
    }

    public Double findReduction(int idOffre, EntityManager em) {
        OffreSiegeAvionVol offre = new OffreSiegeAvionVol();
        offre.findById(idOffre, em);
        EtatOffre etatOffre = offre.getEtatOffre(em);

        if (etatOffre.getNombre() < getNombreSiege()) {
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            return this.getValeurPourcentage();
        }

        return 0.0;
    }
}