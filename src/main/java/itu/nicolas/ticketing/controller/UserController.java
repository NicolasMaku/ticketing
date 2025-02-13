package itu.nicolas.ticketing.controller;

import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.Post;
import mg.itu.prom16.annotations.Url;
import mg.itu.prom16.retourController.ModelView;

@Controller
public class UserController {

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
    public String traitementLogin() {
//        ModelView mv = new ModelView("/webapp/login.jsp");
        return "redirect:/home";
    }
}
