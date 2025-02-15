package itu.nicolas.ticketing.controller;


import itu.nicolas.ticketing.models.Ville;
import itu.nicolas.ticketing.models.Vol;
import itu.nicolas.ticketing.repository.VilleRepository;
import itu.nicolas.ticketing.repository.VolRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.retourController.ModelView;
import mg.itu.prom16.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

@Controller
public class VolController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    VolRepository volRepo  = new VolRepository(em);

    @Get
    @Url("vol")
    public ModelView lister() {
        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/liste.jsp");
        mv.addObject("vols",volRepo.findAll());
        return mv;
    }

    @Get
    @Url("vol/form")
    public ModelView formulaire() {
        VilleRepository vr = new VilleRepository(em);
        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/insertion.jsp");
        mv.addObject("villes",vr.findAll());
        return mv;
    }

    @Post
    @Url("vol/new")
    public String inserer(
            @Param(name = "idVilleDepart") int idVilleDepart,
            @Param(name = "idVilleArrivee") int idVilleArrivee,
            @Param(name = "departVol") String departVol,
            @Param(name = "arriveeVol") String arriveeVol
    ) {
        VilleRepository vr = new VilleRepository(em);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime depart = LocalDateTime.parse(departVol, formatter);
        LocalDateTime arrivee = LocalDateTime.parse(arriveeVol, formatter);

        Ville vd = vr.findById(idVilleDepart);
        Ville va = vr.findById(idVilleArrivee);

        Vol vol = new Vol(depart,arrivee, vd, va);
        volRepo.save(vol);
        return "redirect:/vol";
    }

    @Get
    @Url("vol/delete")
    public String update(
            @Param(name = "id") int idVol
    ) {
        if (idVol != 0)
            volRepo.deleteById(idVol);

        return "redirect:/vol";
    }
}
