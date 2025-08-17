<%@ page import="itu.nicolas.ticketing.models.Reservation" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="itu.nicolas.ticketing.models.ReservationFille" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.format.DateTimeFormatterBuilder" %>
<%
    Reservation res = (Reservation) request.getAttribute("reservation");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div class="g-3 py-3">
    <h3>Reservation n°${reservation.id}</h3>
    <p>Les informations à propos de votre réservation sont les suivantes :</p>
    <div class="section">
        <p><strong>Réservé par :</strong> ${reservation.idUserTicketing.username}</p>
        <p><strong>Date de réservation :</strong>
            <%= res.getDateReservation().format(formatter) %>
        </p>

        <p><strong>Départ :</strong>
            <%= res.getIdVol().getDepartVol().format(formatter) %>
        </p>
        <p><strong>Arrivée :</strong>
            <%= res.getIdVol().getArriveeVol().format(formatter) %>
        </p>
        <p><strong>Avion :</strong> ${reservation.idVol.idAvion.libelle}</p>
    </div>

    <br/>

    <h3>Détails de la réservation :</h3>
    <p>Les billets qui constituent cette réservation sont:</p>

    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Prix</th>
            <th scope="col">Cible</th>
            <th scope="col">Classe</th>
            <th scope="col">Passeport</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<ReservationFille> filles = res.getReservationFilles();
            for (ReservationFille fille : filles) {
        %>
        <tr>
            <td scope="row"><%= fille.getId() %></td>
            <td><%= fille.getPrix() %></td>
            <td><%= (fille.getIdConfigPrix() == null) ? "Adulte" : "Enfant" %></td>
            <td><%= fille.getIdOffreSiegeAvionVol().getIdSiegeAvion().getIdTypeSiege().getLibelle() %></td>
            <td>
                <% if (fille.getImage() != null) { %>
                <img src="data:image/png;base64,<%= fille.getImage() %>" alt="Image passeport"/>
                <% } else { %>
                Aucune
                <% } %>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    </body>
    </html>

</div>