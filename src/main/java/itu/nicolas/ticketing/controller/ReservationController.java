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
        ReservationRepository rr = new ReservationRepository(em);
        EtatOffreRepository etatOffreRepository = new EtatOffreRepository(em);
        VolRepository vr = new VolRepository(em);

        UserTicketing user = userRepo.findById(1);
        OffreSiegeAvionVol offre = offreRepo.findById(idOffre);

        // tester si il reste encore assez de place et si en promotion
        EtatOffre etatOffre = etatOffreRepository.findById(offre.getId());
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

        Reservation res = new Reservation(timeReserv, user, offre);
        rr.save(res);

        return "redirect:/vol/multicritere-front";
    }
}
