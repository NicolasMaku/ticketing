package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.*;
import itu.nicolas.ticketing.repository.*;
import itu.nicolas.ticketing.utils.CsvUtil;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ExportableFile;
import mg.itu.prom16.retourController.ModelView;
import util.CustomSession;
import util.MyFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Authorization("client")
@Controller
public class ReservationController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Get
    @Url("reservation")
    public ModelView choixPlaces(
            @Param(name = "id") int idVol
    ) {
        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);
        VolRepository vr = new VolRepository(em);

        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index_front.jsp");
        mv.addObject("page", "pages/reservation/choixPlaces.jsp");
        mv.addObject("offres", offresRepo.findByVol(vr.findById(idVol)));
        return mv;
    }

    @Post
    @Url("reservation/traitement")
    public String traitement(
            @Param(name = "idVol") Integer idVol,
            @Param(name = "idOffres[]") Integer[] idOffres,
            @Param(name = "image") MyFile image,
            @Param(name = "quantites[]") Integer[] quantites,
            @Param(name = "quantitesEnfants[]") Integer[] quantitesEnfants,
            CustomSession session
    ) throws IOException {
        UserTicketingRepository userRepo = new UserTicketingRepository(em);
        OffreSiegeAvionVolRepository offreRepo = new OffreSiegeAvionVolRepository(em);
        EtatOffreRepository etatOffreRepository = new EtatOffreRepository(em);
        VolRepository vr = new VolRepository(em);

        UserTicketing user = (UserTicketing) session.get("user");
        ConfigPrix cpEnfant = new ConfigPrix();
        cpEnfant.setId(1);
        cpEnfant.findById(em);


        // regarder si avant heure fin reservation
        Vol vol = vr.findById(idVol);
        LocalDateTime timeReserv = LocalDateTime.now();
        ConfigurationLimite confRes = new ConfigurationLimite();
        confRes.findByLibelle("reservation", em);
        LocalDateTime limite = vol.getDepartVol().minusHours(confRes.getNbreHeure());
        System.out.println(limite);
        if (timeReserv.isAfter(limite)) {
            String message = "Vous avez depassee le temps limite de reservation";
            return "redirect:/vol/multicritere-front?erreur=" + message;
        }

        Reservation res = new Reservation(timeReserv, user);
        res.setImage(image.getBytes());
        res.setIdVol(vol);

        List<ReservationFille> resFilles = new ArrayList<>();
        // tester si il reste encore assez de place et si en promotion
        int qteTotaleEnfant = 0;
        int qteTotaleAdulte = 0;
        for (int i = 0; i < quantites.length; i++) {
            int sommeQte = quantitesEnfants[i] + quantites[i];
            qteTotaleEnfant += quantitesEnfants[i];
            qteTotaleAdulte += quantites[i];

            EtatOffre etatOffre = etatOffreRepository.findByIdOffre(idOffres[i]);
            if (etatOffre.getNombre() +qteTotaleAdulte +qteTotaleEnfant > etatOffre.getIdOffreSiegeAvionVol().getIdSiegeAvion().getNombre()) {
                String message = "Il n y a plus de place";
                return "redirect:/vol/multicritere-front?erreur=" + message;
            }
        }


        // sauvegarder si aucune anomalie
        res.save(em);

        for (int i = 0; i < quantites.length; i++) {

            OffreSiegeAvionVol offre = new OffreSiegeAvionVol();
            offre.findById(idOffres[i], em);

            // reste de promotion

            // getter si il y a un promotion
            Double prix = offre.getPrix();
            Promotion prom = new Promotion();
            prom.findByIdOffre(idOffres[i], em);
            int promotion_reste = 0;

            if (prom.getId() != null) {
                promotion_reste = prom.getNombreSiege() - offre.getEtatOffre(em).getNombre();
            }




            if (quantites[i] > 0) {
                for (int j = 0; j < quantites[i]; j++) {
                    prix = offre.getPrix();

                    if (prom.getId() != null && promotion_reste > 0 ) {
                        prix = (prix - (prix*prom.findReduction(idOffres[i], em)/100));
                        promotion_reste -= 1;
                    }

                    ReservationFille resFille = new ReservationFille(prix,offre, res,null);
                    resFilles.add(resFille);
                }
            }

            if (quantitesEnfants[i] > 0) {
                for (int j = 0; j < quantitesEnfants[i] ; j++) {
                    double reduc = 0.0;
                    prix = offre.getPrix();

                    if (prom.getId() != null && promotion_reste > 0 ) {
                        reduc += prom.findReduction(idOffres[i], em);
                        promotion_reste -= 1;
                    }

                    reduc += cpEnfant.getReduction();
                    prix = (prix - (prix*reduc/100));

                    ReservationFille resFille = new ReservationFille(prix,offre, res,cpEnfant);
                    resFilles.add(resFille);
                }
            }
//            ReservationFille resFille = new ReservationFille(prix,offre, res,prix);

//            res.save(em);
        }

        if (!resFilles.isEmpty()) {
            resFilles.get(0).saveAll(resFilles, em);
        }

//        Reservation res = new Reservation(timeReserv, user, offre,prix);
//        res.setPasseport(image.getBytes());
//        res.save(em);

//        image.sauvegarder("C:/Users/nicol/Documents/0-ITU/S5/ticketing/assets");

        return "redirect:/vol/multicritere-front";
    }

    @Get
    @Url("reservation/liste")
    public ModelView liste(
            @Param(name = "erreur") String erreur,
            CustomSession session
    ) {
        UserTicketing u = (UserTicketing) session.get("user");
        if (u == null) return new ModelView("redirect:/ticketing/login");

        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);
        VolRepository vr = new VolRepository(em);

        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index_front.jsp");
        mv.addObject("page", "pages/reservation/liste.jsp");
        mv.addObject("reservations", u.findAllReservationEnCours(em));
        mv.addObject("reservationsFini", u.findAllReservationFini(em));

        if (erreur != null) mv.addObject("erreur", erreur);
        return mv;
    }

    @Post
    @Url("reservation/annuler")
    public String annuler(
            @Param(name = "id") int idReservation,
            CustomSession session
    ) {
        UserTicketing u = (UserTicketing) session.get("user");
        int idUser = u.getId();

        Reservation res = new Reservation();
        res.findById(idReservation, em);

        // voir si date d'annulation apres limite de heure
        ConfigurationLimite limAnnulation = new ConfigurationLimite();
        limAnnulation.findByLibelle("annulation", em);
        LocalDateTime limiteAnnulation = res.getReservationFilles(em).get(0).getIdOffreSiegeAvionVol().getIdVol().getDepartVol().minusHours(limAnnulation.getNbreHeure());
        if (limiteAnnulation.isBefore(LocalDateTime.now())) {
            String message = "Vous avez depassee l heure limite d annulation";
            System.out.println(message);
            return "redirect:/reservation/liste?idUser=" + idUser + "&&erreur=" + message;
        }

        res.setDateAnnulation(LocalDateTime.now());
        res.update(em);
        return "redirect:/reservation/liste";
    }

}
