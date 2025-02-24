package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.ConfigurationLimite;
import itu.nicolas.ticketing.repository.ConfigurationLimiteRepository;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;

@Authorization("admin")
@Controller
public class ConfigurationLimiteController {
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
    ConfigurationLimiteRepository clr = new ConfigurationLimiteRepository(em);

    @Get
    @Url("configurations/form")
    public ModelView formulaire() {
        mg.itu.prom16.retourController.ModelView mv = new mg.itu.prom16.retourController.ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/configuration/configuration.jsp");
        mv.addObject("configs",clr.findAll());
        return mv;
    }

    @Post
    @Url("configurations/traitement")
    public String traitement(
            @Param(name = "idConfig[]") Integer[] idConfigs,
            @Param(name = "heures[]") Integer[] heures
    ) {

        for (int i = 0; i < idConfigs.length; i++) {
            ConfigurationLimite conf = clr.findById(idConfigs[i]);
            conf.setNbreHeure(heures[i]);
            clr.save(conf);
        }
        return "redirect:/configurations/form";
    }
}
