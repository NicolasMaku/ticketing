<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.beans.Encoder" %>
<%@ page import="java.util.Base64" %>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    List<ReservationFille> rfs = (List<ReservationFille>) request.getAttribute("filles");
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
<h3>Réservations en cours <%=reservations.size()%></h3>
<div class="g-3 py-3">
    <table class="table table-hover align-middle">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Date res</th>
            <th scope="col">Nbr billets</th>
<%--            <th scope="col">Classe</th>--%>
            <th scope="col">Trajet</th>
            <th scope="col">Avion</th>
            <th scope="col">Decollage</th>
            <th scope="col">Arrivée</th>
            <th scope="col">passeport</th>
            <th scope="col">Actions</th>
            <th scope="col">Export</th>
        </tr>
        </thead>
        <tbody>
        <% for(Reservation r : reservations) { %>
        <tr class="">
            <th scope="row"><%=r.getId()%></th>
            <th class=""><%=r.getDateReservation().format(formatter)%></th>
            <th class=""><%=r.getReservationFilles().size()%></th>
<%--            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdSiegeAvion().getIdTypeSiege().getLibelle()%></td>--%>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleDepart().getNom()%> - <%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdVilleArrivee().getNom()%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getIdAvion().getLibelle()%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getDepartVol().format(formatter)%></td>
            <td><%=r.getReservationFilles().get(0).getIdOffreSiegeAvionVol().getIdVol().getArriveeVol().format(formatter)%></td>
            <td><% if (r.getImage() != null) { %>
                <img style="height: 3rem" src="data:image/png;base64,<%=Base64.getEncoder().encodeToString(r.getImage())%>" alt="">
            <% } %> </td>
            <td class="">
                <% if (r.getDateAnnulation() == null) { %>
                    <form action="/ticketing/reservation/annuler/admin" method="post">
                        <input type="hidden" name="id" value="<%=r.getId()%>">
                        <input type="hidden" name="idUser" value="1">
                        <button type="submit" class="btn btn-primary btn-sm">
                            <i class="bi bi-x"></i>Annuler
                        </button>
                    </form>

                <% } else { %>
                    Annulé
                <% } %>
            </td>
            <td>
                <button class="btn btn-primary btn-sm" type="button" onclick="telechargerPDF(<%=r.getId()%>)">Télécharger PDF</button>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script>
    function telechargerPDF(id) {
        fetch(`http://localhost:8080/api/reservation/pdf/13`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erreur : " + response.status);
                }
                return response.blob(); // On récupère le PDF en binaire
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);

                const a = document.createElement("a");
                a.href = url;
                a.download = `reservation-13.pdf`; // Nom du fichier
                document.body.appendChild(a);
                a.click(); // Déclenche le téléchargement
                a.remove();
                window.URL.revokeObjectURL(url); // Libère la mémoire
                alert("L'export est reussi");
            })
            .catch(error => {
                alert("Impossible de télécharger le fichier : " + error.message);
            });
    }
</script>