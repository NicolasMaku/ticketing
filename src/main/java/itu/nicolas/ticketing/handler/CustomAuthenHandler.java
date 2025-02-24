package itu.nicolas.ticketing.handler;

import itu.nicolas.ticketing.models.UserTicketing;
import jakarta.servlet.http.HttpServletRequest;
import mg.itu.prom16.RoleHandler;
import itu.nicolas.ticketing.models.User;

public class CustomAuthenHandler implements RoleHandler {
    @Override
    public boolean isAllowedUser(HttpServletRequest req, String[] roles) {
        return false;
    }

    @Override
    public boolean isAuthenticated(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }


    @Override
    public String getRole(HttpServletRequest req) {
        try {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
            UserTicketing user = (UserTicketing) req.getSession().getAttribute("user");
            if (user == null) System.out.println("NNNNNNNNNNNNNUUUUUUUUUUUUUUUULLLLLLLLLLLLLLLLLLLLLL");
            else System.out.println("NNNNNNNNNNNNNNNNNNNNOOOOOOOOOOOOOOOOOOOOOO");
            return user.getIdRole().getLibelle();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
