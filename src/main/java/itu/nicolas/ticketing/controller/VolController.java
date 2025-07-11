package itu.nicolas.ticketing.controller;


import itu.nicolas.ticketing.models.*;
import itu.nicolas.ticketing.repository.*;
import itu.nicolas.ticketing.utils.CsvUtil;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.Role;
import mg.itu.prom16.retourController.ExportableFile;
import mg.itu.prom16.retourController.ModelView;
import mg.itu.prom16.annotations.*;
import util.CustomSession;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;

@Controller
public class VolController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    VolRepository volRepo  = new VolRepository(em);

    @Get
    @Url("vol")
    @Role("admin")
    public ModelView lister() {
        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/liste.jsp");
        mv.addObject("vols",volRepo.findAll());
        return mv;
    }

    @Get
    @Url("vol/form")
    @Role("admin")
    public ModelView formulaire() {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);

        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/insertion.jsp");
        mv.addObject("villes",vr.findAll());
        mv.addObject("avions",ar.findAll());
        return mv;
    }

    @Post
    @Url("vol/formPrix")
    @Role("admin")
    public ModelView formulairePrix(
            @Model("avion") Avion avion,
            @Model("vol") Vol vol
    ) {
        AvionRepository ar = new AvionRepository(em);
        GenericRepository<TypeSiege> typeSiegeRepo = new GenericRepository<TypeSiege>(em, TypeSiege.class);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);
        VolRepository vr = new VolRepository(em);
        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);

        avion = ar.findById(avion.getId());
        vol.findById(vol.getId(), em);
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII " + vol.getId());

        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/vol/insertionPrixPlaces.jsp");
        mv.addObject("avion", avion);
        mv.addObject("siegesAvion", sar.findByAvion(avion));
        mv.addObject("vol", vol);

        if (vol.getId() > 0) {
            mv.addObject("offresSiege", offresRepo.findByVol(vol));
//            mv.addObject("offresSiege", vol.getOffreSiegeAvionVols());
        }

        return mv;
    }

    @Post
    @Url("vol/new")
    @Role("admin")
    public String inserer(
            @Model("vol") Vol vol,
            @Model("v1") Ville villeDepart,
            @Model("v2") Ville villeArrivee,
            @Param(name = "departVol") String departVol,
            @Param(name = "arriveeVol") String arriveeVol,
            @Model("avion") Avion avion,
            @Param(name = "idSiegesAvion[]") Integer[] idSiegesAvion,
            @Param(name = "prix[]") Double[] prix,
            @Param(name = "quantite[]") Integer[] nbrePlaces,
            @Param(name = "pourc[]") Double[] poucentages ,
            @Param(name = "idProm[]") Integer[] idPromotions,
            @Param(name = "idOffres[]") Integer[] idOffres
    ) {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);
        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime depart = LocalDateTime.parse(departVol, formatter);
        LocalDateTime arrivee = LocalDateTime.parse(arriveeVol, formatter);


        if (vol.getId() <0) {
            System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHh");
            Vol vol2 = new Vol(depart,arrivee, villeDepart, villeArrivee, avion);
            volRepo.save(vol2);

            // creation des offres de places et promotion
            List<OffreSiegeAvionVol> offres = new ArrayList<>();
            List<Promotion> promotions = new ArrayList<>();

            for (int i = 0; i < idSiegesAvion.length ; i++) {
                SiegeAvion siege = sar.findById(idSiegesAvion[i]);
                OffreSiegeAvionVol offre = new OffreSiegeAvionVol(prix[i], siege, vol2);
                offres.add(offre);

                if (nbrePlaces[i] > 0) {
                    Promotion prom = new Promotion(poucentages[i], nbrePlaces[i], offre);
                    promotions.add(prom);
                }
            }
            offresRepo.saveAll(offres);
            if (!promotions.isEmpty())
                promotions.get(0).saveAll(promotions, em);
            else {
                Promotion p = new Promotion();
                p.saveAll(promotions, em);
            }

        } else {
            Vol ancien = volRepo.findById(vol.getId());
            Vol nouv = new Vol(depart,arrivee, villeDepart, villeArrivee, avion);
            nouv.setId(ancien.getId());

            for (int i = 0; i < idOffres.length ; i++) {
                OffreSiegeAvionVol offre = new OffreSiegeAvionVol();
                offre.findById(idOffres[i], em);
                SiegeAvion siege = sar.findById(idSiegesAvion[i]);
                OffreSiegeAvionVol offreNouv = new OffreSiegeAvionVol(prix[i], siege, nouv);
                offreNouv.setId(offre.getId());
                offresRepo.update(offreNouv);
            }

            // update des promotions
            for (int i = 0; i < idPromotions.length; i++) {
                Promotion p = new Promotion();
                p.findById(idPromotions[i], em);
                if (p.getId() != null) {
                    p.setNombreSiege(nbrePlaces[i]);
                    p.setValeurPourcentage(poucentages[i]);
                    p.update(em);
                }
            }

            volRepo.update(nouv);
        }

        return "redirect:/vol";
    }

//    @Post
//    @Url("vol/new")
//    @Role("admin")
//    public String inserer(
//            @Param(name = "idVilleDepart") int idVilleDepart,
//            @Param(name = "idVilleArrivee") int idVilleArrivee,
//            @Param(name = "departVol") String departVol,
//            @Param(name = "arriveeVol") String arriveeVol,
//            @Param(name = "idAvion") int idAvion,
//            @Param(name = "idSiegesAvion[]") Integer[] idSiegesAvion,
//            @Param(name = "prix[]") Double[] prix,
//            @Param(name = "quantite[]") Integer[] nbrePlaces,
//            @Param(name = "pourc[]") Double[] poucentages ,
//            @Param(name = "idProm[]") Integer[] idPromotions,
//            @Param(name = "idVol") int idVol,
//            @Param(name = "idOffres[]") Integer[] idOffres
//    ) {
//        VilleRepository vr = new VilleRepository(em);
//        AvionRepository ar = new AvionRepository(em);
//        SiegeAvionRepository sar = new SiegeAvionRepository(em);
//        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        LocalDateTime depart = LocalDateTime.parse(departVol, formatter);
//        LocalDateTime arrivee = LocalDateTime.parse(arriveeVol, formatter);
//
//        Ville vd = vr.findById(idVilleDepart);
//        Ville va = vr.findById(idVilleArrivee);
//
//        Avion avion = ar.findById(idAvion);
//
//
//        if (idVol <0) {
//            Vol vol = new Vol(depart,arrivee, vd, va, avion);
//            volRepo.save(vol);
//
//            // creation des offres de places et promotion
//            List<OffreSiegeAvionVol> offres = new ArrayList<>();
//            List<Promotion> promotions = new ArrayList<>();
//
//            for (int i = 0; i < idSiegesAvion.length ; i++) {
//                SiegeAvion siege = sar.findById(idSiegesAvion[i]);
//                OffreSiegeAvionVol offre = new OffreSiegeAvionVol(prix[i], siege, vol);
//                offres.add(offre);
//
//                if (nbrePlaces[i] > 0) {
//                    Promotion prom = new Promotion(poucentages[i], nbrePlaces[i], offre);
//                    promotions.add(prom);
//                }
//            }
//            offresRepo.saveAll(offres);
//            promotions.get(0).saveAll(promotions, em);
//
//        } else {
//            Vol ancien = volRepo.findById(idVol);
//            Vol nouv = new Vol(depart,arrivee, vd, va, avion);
//            nouv.setId(ancien.getId());
//
//            for (int i = 0; i < idOffres.length ; i++) {
//                OffreSiegeAvionVol offre = new OffreSiegeAvionVol();
//                offre.findById(idOffres[i], em);
//                SiegeAvion siege = sar.findById(idSiegesAvion[i]);
//                OffreSiegeAvionVol offreNouv = new OffreSiegeAvionVol(prix[i], siege, nouv);
//                offreNouv.setId(offre.getId());
//                offresRepo.update(offreNouv);
//            }
//
//            // update des promotions
//            for (int i = 0; i < idPromotions.length; i++) {
//                Promotion p = new Promotion();
//                p.findById(idPromotions[i], em);
//                if (p.getId() != null) {
//                    p.setNombreSiege(nbrePlaces[i]);
//                    p.setValeurPourcentage(poucentages[i]);
//                    p.update(em);
//                }
//            }
//
//            volRepo.update(nouv);
//        }
//
//        return "redirect:/vol";
//    }

    @Get
    @Url("vol/multicritere")
    @Role("admin")
    public ModelView multicritere(

    ) {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);
        VolRepository volrepo = new VolRepository(em);

        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/vol/rechercheMulti.jsp");
        mv.addObject("vols", volRepo.findAll());
        mv.addObject("villes",vr.findAll());
        mv.addObject("avions",ar.findAll());
        return mv;
    }

    @Get
    @Url("vol/multicritere-front")
    @Role("client")
    public ModelView multicritereFront(
        @Param(name = "erreur") String erreur
    ) {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);
        VolRepository volrepo = new VolRepository(em);

        ModelView mv = new ModelView("/webapp/index_front.jsp");
        mv.addObject("page", "pages/vol/rechercheMultiFront.jsp");
        mv.addObject("vols", volRepo.findAll());
        mv.addObject("villes",vr.findAll());
        mv.addObject("avions",ar.findAll());

        if (erreur != null) mv.addObject("erreur", erreur);
        return mv;
    }

    @Post
    @Url("vol/traite-multi")
    @Role
    public ModelView traitementMulticritere(
        @Param(name = "idVilleDepart") Integer idVilleDepart,
        @Param(name = "idVilleArrivee") Integer idVilleArrivee,
        @Param(name = "departVol") String departVol,
        @Param(name = "arriveeVol") String arriveeVol,
        @Param(name = "idAvion") Integer idAvion,
        @Param(name = "prixMin") Double prixMin,
        @Param(name = "prixMax") Double prixMax,
        CustomSession session
    ) {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        SiegeAvionRepository sar = new SiegeAvionRepository(em);
        VolRepository volrepo = new VolRepository(em);

        if (prixMax == 0) prixMax = null;
        if (idVilleArrivee < 0) idVilleArrivee = null;
        if (idVilleDepart < 0) idVilleDepart = null;
        if (idAvion < 0) idAvion = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime depart = null;
        if (!Objects.equals(departVol, "null")) depart = LocalDateTime.parse(departVol, formatter);
        LocalDateTime arrivee = null;
        if (!Objects.equals(arriveeVol, "null")) depart = LocalDateTime.parse(arriveeVol, formatter);

        List<Vol> corresp = volrepo.rechercheMulti(idVilleDepart, idVilleArrivee, depart, arrivee, idAvion, prixMin, prixMax);

        ModelView mv = new ModelView("/webapp/index.jsp");

        UserTicketing u = (UserTicketing) session.get("user");
        if (u.getIdRole().getId() == 2) mv.addObject("page", "pages/vol/rechercheMulti.jsp");
        else if (u.getIdRole().getId() == 1) {
            mv.setUrl("/webapp/index_front.jsp");
            mv.addObject("page", "pages/vol/rechercheMultiFront.jsp");
        }
        mv.addObject("vols",corresp);
        mv.addObject("villes",vr.findAll());
        mv.addObject("avions",ar.findAll());

        return mv;
    }

    @Get
    @Url("vol/delete")
    @Role("admin")
    public String update(
            @Param(name = "id") int idVol
    ) {
        if (idVol != 0)
            volRepo.deleteById(idVol);

        return "redirect:/vol";
    }

    @Get
    @Url("vol/update")
    @Role("admin")
    public ModelView formUpdate(
            @Param(name = "id") int idVol
    ) {
        VilleRepository vr = new VilleRepository(em);
        AvionRepository ar = new AvionRepository(em);
        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/insertion.jsp");
        mv.addObject("avions",ar.findAll());
        mv.addObject("villes",vr.findAll());
        mv.addObject("vol", volRepo.findById(idVol));
        return mv;
    }


    @Get
    @Url("vol/export-csv")
    public ExportableFile exportCsv() {
        ExportableFile csv = new ExportableFile("vols.csv", "text/csv");

        Vol vl = new Vol();
        List<Vol> vols = vl.findAll(em);

        csv.setContent(CsvUtil.exportToCsvBytes(vols, Vol.class));
        return csv;
    }

}
