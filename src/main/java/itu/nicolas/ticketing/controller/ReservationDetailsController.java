package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.Reservation;
import itu.nicolas.ticketing.repository.ReservationRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;

@Controller
public class ReservationDetailsController {

    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Role("admin")
    @Get
    @Url("reservation-details-back")
    public ModelView getDetailsBack(
            @Param(name = "idReservation") int idReservation
    ) {
        ReservationRepository reservationRepository = new ReservationRepository(em);
        Reservation res = reservationRepository.findById(idReservation);
        ModelView mv = new ModelView("/webapp/index.jsp");

        if (res != null) {
            mv.addObject("reservation", res);
            mv.addObject("page", "pages/reservation/details.jsp");
        } else {
            mv.addObject("page", "pages/reservation/details.jsp");
        }

        return mv;
    }

    @Role("client")
    @Get
    @Url("reservation-details")
    public ModelView getDetailsFront(
            @Param(name = "idReservation") int idReservation
    ) {
        ReservationRepository reservationRepository = new ReservationRepository(em);
        Reservation res = reservationRepository.findById(idReservation);
        ModelView mv = new ModelView("/webapp/index_front.jsp");

        if (res != null) {
            mv.addObject("reservation", res);
            mv.addObject("page", "pages/reservation/details.jsp");
        } else {
            mv.addObject("page", "pages/reservation/details.jsp");
        }

        return mv;
    }

}
