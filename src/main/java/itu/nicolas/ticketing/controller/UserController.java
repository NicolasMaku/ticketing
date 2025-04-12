package itu.nicolas.ticketing.controller;

import itu.nicolas.ticketing.models.UserTicketing;
import itu.nicolas.ticketing.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.retourController.ModelView;
import util.CustomSession;
import util.FormValidation;

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
    public ModelView traitementLogin(
            @Model("user") UserTicketing user,
//            @Param(name = "email") String email,
//            @Param(name = "password") String mdp,
            CustomSession session
    ) throws IllegalAccessException {
        ModelView mv = new ModelView("/webapp/index.jsp");
        if (!FormValidation.isValid(user)) {
            mv.setErrorUrl("/login");
            return mv;
        }

        if (user.findByLogin(em)) {
            session.add("user", user);
            if (user.getIdRole().getId() == 1) mv.setUrl("redirect:/ticketing/vol/multicritere-front");
            if (user.getIdRole().getId() == 2) mv.setUrl("redirect:/ticketing/vol/multicritere");
        }
        else mv.setUrl("/webapp/login.jsp");

        return mv;
    }

    @Get
    @Url("deconnexion")
    public String deconnexion(CustomSession session) {
        session.delete("user");
        return "redirect:/login";
    }
}
