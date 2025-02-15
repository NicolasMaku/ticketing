package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.repository.SiegeAvionRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.retourController.ModelView;
import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.Url;

@Controller
public class HomeController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    @Get
    @Url("home")
    public String home() {
//        ModelView mv = new ModelView("/webapp/index.jsp");
//        mv.addObject("page", "pages/insertion.jsp");
        SiegeAvionRepository siegeAvionRepository = new SiegeAvionRepository(em);
        return "" + siegeAvionRepository.findAll().get(0).getNombre();
    }
}
