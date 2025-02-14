package itu.nicolas.ticketing.controller;

import mg.itu.prom16.retourController.ModelView;
import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.Url;

@Controller
public class HomeController {
    @Get
    @Url("home")
    public ModelView home() {
        ModelView mv = new ModelView("/webapp/index.jsp");
        mv.addObject("page", "pages/insertion.jsp");
        return mv;
    }
}
