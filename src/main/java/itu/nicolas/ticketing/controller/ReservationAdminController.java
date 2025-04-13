package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.ConfigurationLimite;
import itu.nicolas.ticketing.models.Reservation;
import itu.nicolas.ticketing.models.ReservationFille;
import itu.nicolas.ticketing.models.UserTicketing;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;
import util.CustomSession;

import java.time.LocalDateTime;

@Authorization("admin")
@Controller
public class ReservationAdminController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Get
    @Url("reservation/liste-admin")
    public ModelView listeReservAdmin(
            @Param(name = "erreur") String erreur,
            CustomSession session
    ) {
        UserTicketing u = (UserTicketing) session.get("user");
        if (u == null) return new ModelView("redirect:/ticketing/login");

        Reservation reservation = new Reservation();

        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/reservation/liste-admin.jsp");
        mv.addObject("reservations", reservation.findAll());

        if (erreur != null) mv.addObject("erreur", erreur);
        return mv;
    }

    @Post
    @Url("reservation/annuler/admin")
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
