<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.beans.Encoder" %>
<%@ page import="java.util.Base64" %>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    List<Reservation> reservationsFini = (List<Reservation>) request.getAttribute("reservationsFini");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    String erreur = (String) request.getAttribute("erreur");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% if (erreur != null) { %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%=erreur%>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% } %>
<h3>Réservations en cours</h3>
<div class="g-3 py-3">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Trajet</th>
            <th scope="col">Avion</th>
            <th scope="col">Decollage</th>
            <th scope="col">Arrivée</th>
            <th scope="col">passeport</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for(Reservation r : reservations) {
            if (!r.getReservationFilles().isEmpty()) { %>

            <tr class="">
                <th scope="row"><%=r.getId()%></th>
                <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleDepart().getNom()%> - <%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleArrivee().getNom()%></td>
                <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdAvion().getLibelle()%></td>
                <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getDepartVol().format(formatter)%></td>
                <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getArriveeVol().format(formatter)%></td>
                <td><% if (r.getImage() != null) { %>
                    <img style="height: 3rem" src="data:image/png;base64,<%=Base64.getEncoder().encodeToString(r.getImage())%>" alt="">
                <% } %> </td>
                <td>
                    <% if (r.getDateAnnulation() == null) { %>
                        <form action="/ticketing/reservation/annuler" method="post">
                            <input type="hidden" name="id" value="<%=r.getId()%>">
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="bi bi-x"></i>Annuler
                            </button>
                        </form>
                    <% } else { %>
                        Annulé
                    <% } %>
                </td>
            </tr>
            <% } %>
        <% } %>
        </tbody>
    </table>
</div>

<h3>Réservations vol passé</h3>
<div class="g-3 py-3">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Classe</th>
            <th scope="col">Trajet</th>
            <th scope="col">Avion</th>
            <th scope="col">Decollage</th>
            <th scope="col">Arrivée</th>
        </tr>
        </thead>
        <tbody>
        <% for(Reservation r : reservationsFini) { %>
        <tr class="">
            <th scope="row"><%=r.getId()%></th>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdSiegeAvion().getIdTypeSiege().getLibelle()%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleDepart().getNom()%> - <%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleArrivee().getNom()%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdAvion().getLibelle()%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getDepartVol().format(formatter)%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getArriveeVol().format(formatter)%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
