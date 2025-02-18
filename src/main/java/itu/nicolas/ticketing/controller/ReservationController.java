package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.*;
import itu.nicolas.ticketing.repository.*;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;

import java.time.LocalDateTime;
import java.util.List;

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

        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/reservation/choixPlaces.jsp");
        mv.addObject("offres", offresRepo.findByVol(vr.findById(idVol)));
        return mv;
    }

    @Post
    @Url("reservation/traitement")
    public String traitement(
            @Param(name = "idVol") Integer idVol,
            @Param(name = "idOffre") Integer idOffre
    ) {
        UserTicketingRepository userRepo = new UserTicketingRepository(em);
        OffreSiegeAvionVolRepository offreRepo = new OffreSiegeAvionVolRepository(em);
        EtatOffreRepository etatOffreRepository = new EtatOffreRepository(em);
        VolRepository vr = new VolRepository(em);

        UserTicketing user = userRepo.findById(1);
        OffreSiegeAvionVol offre = new OffreSiegeAvionVol();
        offre.findById(idOffre, em);

        // tester si il reste encore assez de place et si en promotion
        EtatOffre etatOffre = etatOffreRepository.findByIdOffre(offre.getId());
        if (etatOffre.getNombre() +1 > etatOffre.getIdOffreSiegeAvionVol().getIdSiegeAvion().getNombre()) {
            String message = "Il n y a plus de place";
            return "redirect:/vol/multicritere-front?erreur=" + message;
        }

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

        // getter si il y a un promotion
        Double prix = offre.getPrix();
        Promotion prom = new Promotion();
        prom.findByIdOffre(idOffre, em);

        if (prom.getId() != null) {
            prix = (prix - (prix*prom.findReduction(idOffre, em)/100));
        }

        Reservation res = new Reservation(timeReserv, user, offre,prix);
        res.save(em);

        return "redirect:/vol/multicritere-front";
    }

    @Get
    @Url("reservation/liste")
    public ModelView liste(
            @Param(name = "idUser") int idUser,
            @Param(name = "erreur") String erreur
    ) {
        OffreSiegeAvionVolRepository offresRepo = new OffreSiegeAvionVolRepository(em);
        VolRepository vr = new VolRepository(em);

        UserTicketing u = new UserTicketing();
        u.findById(1, em);

        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index.jsp");
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
            @Param(name = "idUser") int idUser
    ) {
        Reservation res = new Reservation();
        res.findById(idReservation, em);

        // voir si date d'annulation apres limite de heure
        ConfigurationLimite limAnnulation = new ConfigurationLimite();
        limAnnulation.findByLibelle("annulation", em);
        LocalDateTime limiteAnnulation = res.getIdOffreSiegeAvionVol().getIdVol().getDepartVol().minusHours(limAnnulation.getNbreHeure());
        if (limiteAnnulation.isBefore(LocalDateTime.now())) {
            String message = "Vous avez depassee l heure limite d annulation";
            System.out.println(message);
            return "redirect:/reservation/liste?idUser=" + idUser + "&&erreur=" + message;
        }

        idUser = 1;
        res.setDateAnnulation(LocalDateTime.now());
        res.update(em);
        return "redirect:/reservation/liste?idUser=" + idUser;
    }

}
