package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.UserTicketing;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;
import util.CustomSession;

@Controller
public class UserController {

    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Get
    @Url("formulaire")
    public ModelView formulaire() {
        ModelView mv = new ModelView("/webapp/index.jsp");
        return mv;
    }

    @Get
    @Url("login")
    public ModelView login() {
        ModelView mv = new ModelView("/webapp/login.jsp");
        return mv;
    }

    @Post
    @Url("login/traitement")
    public String traitementLogin(
            @Param(name = "email") String email,
            @Param(name = "mdp") String mdp,
            CustomSession session
    ) {
        UserTicketing user = new UserTicketing(email, mdp);
        if (user.findByLogin(em)) {
            session.add("user", user);
            return "redirect:/vol/multicritere-front";
        }

        String message = "Mauvais login";
        return "redirect:/login?erreur=" + message;
    }

    @Get
    @Url("deconnexion")
    public String deconnexion(CustomSession session) {
        session.delete("user");
        return "redirect:/login";
    }
}
