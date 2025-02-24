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
    <% for(OffreSiegeAvionVol o : offresSiege) { %>
        <div class="mb-3 row">
<%--            <label for="staticEmail" class="col-sm-2 col-form-label">Choisisez votre classe</label>--%>
            <div class="input-group mb-3">
                <%
                    EtatOffre etatOffre = o.getEtatOffre(em);
                    int soustraire = 0;
                    if (etatOffre != null) soustraire = etatOffre.getNombre();
                %>
                <input class="mt-0 mx-3 p-3" type="radio" name="idOffre" aria-label="Checkbox for following text input" value="<%=o.getId()%>">
                <div><%=o.getIdSiegeAvion().getIdTypeSiege().getLibelle() + " : " + o.getPrix() %>$ ; restant: <%=o.getIdSiegeAvion().getNombre() - soustraire%> </div>
            </div>
        </div>
    <% } %>
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

