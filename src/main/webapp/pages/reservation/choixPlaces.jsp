<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="itu.nicolas.ticketing.utils.JPAUtil" %>
<%
    List<OffreSiegeAvionVol> offresSiege = (List<OffreSiegeAvionVol>) request.getAttribute("offres");
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="row g-3" action="/ticketing/reservation/traitement" method="post" enctype="multipart/form-data">

    <h3>Reservation</h3>
    <p>Choisissez votre classe</p>
    <input type="hidden" name="idVol" value="<%=request.getParameter("id")%>">
    <table>
        <tr>
            <th>Sélection</th>
            <th>Type de billet</th>
            <th>Prix</th>
            <th>Restant</th>
            <th>Quantité adulte</th>
            <th>Quantité enfant</th>
        </tr>
    <% for(OffreSiegeAvionVol o : offresSiege) { %>

            <tr>
                <td>
                    <input type="checkbox" name="idOffres[]" value="<%= o.getId() %>" onchange="toggleQuantityInput(this)">
                </td>
                <td>
                    <%
                        EtatOffre etatOffre = o.getEtatOffre(em);
                        int soustraire = 0;
                        if (etatOffre != null) soustraire = etatOffre.getNombre();
                    %>
                    <%=o.getIdSiegeAvion().getIdTypeSiege().getLibelle() %>
                </td>
                <td><%= o.getPrix() %> $</td>
                <td><%=o.getIdSiegeAvion().getNombre() - soustraire%></td>
                <td>
                    <input type="number" name="quantites[]" min="0" max="10" value="1" disabled>
                </td>
                <td>
                    <input type="number" name="quantitesEnfants[]" min="0" max="10" value="0" disabled>
                </td>
            </tr>

    <% } %>
    </table>


<%--    <%=vol.getId()%>--%>
<%--    <%=offresSiege.isEmpty()%>--%>
<%--    <%=offresSiege.size()%>--%>
    <div>
        <p>Inserer passeport</p>
        <input type="file" name="image" >
    </div>
    <div class="col-12">
        <button class="btn btn-primary" type="submit">Valider formulaire</button>
    </div>

</form>
<script>
    function toggleQuantityInput(checkbox) {
        const inputs = checkbox.closest('tr').querySelectorAll('input[type="number"]');
        inputs.forEach((input) => {
            input.disabled = !checkbox.checked;
        })
    }
</script>

