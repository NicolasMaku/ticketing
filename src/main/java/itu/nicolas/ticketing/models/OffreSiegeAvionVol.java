package itu.nicolas.ticketing.models;

import itu.nicolas.ticketing.repository.EtatOffreRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "offre_siege_avion_vol")
public class OffreSiegeAvionVol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offre_siege_avion_vol", nullable = false)
    private Integer id;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_siege_avion", nullable = false)
    private SiegeAvion idSiegeAvion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vol", nullable = false)
    private Vol idVol;

    @OneToOne(mappedBy = "idOffreSiegeAvionVol")
    private Promotion promotion;

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public SiegeAvion getIdSiegeAvion() {
        return idSiegeAvion;
    }

    public void setIdSiegeAvion(SiegeAvion idSiegeAvion) {
        this.idSiegeAvion = idSiegeAvion;
    }

    public Vol getIdVol() {
        return idVol;
    }

    public void setIdVol(Vol idVol) {
        this.idVol = idVol;
    }

    public OffreSiegeAvionVol() {
    }

    public OffreSiegeAvionVol(Double prix, SiegeAvion idSiegeAvion, Vol idVol) {
        this.prix = prix;
        this.idSiegeAvion = idSiegeAvion;
        this.idVol = idVol;
    }

    public EtatOffre getEtatOffre(EntityManager em) {
        EtatOffreRepository etatOffre = new EtatOffreRepository(em);
        return etatOffre.findByIdOffre(this.getId());
    }

    public EtatOffre getEtatOffre() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return getEtatOffre(em);
    }

    public void findById(int id, EntityManager em) {
        try {
            OffreSiegeAvionVol offre = em.find(OffreSiegeAvionVol.class, id);
            this.adapt(offre);
        } catch (Exception e) {
            return ;
        }
    }

    public void adapt(OffreSiegeAvionVol o) {
        this.id = o.getId();
        this.idVol = o.getIdVol();
        this.idSiegeAvion = o.getIdSiegeAvion();
        this.prix = o.getPrix();
    }

    public OffreSiegeAvionVol(Integer id) {
        this.id = id;
    }
}