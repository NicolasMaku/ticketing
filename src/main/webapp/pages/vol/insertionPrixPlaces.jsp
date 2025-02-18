<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%
    List<SiegeAvion> siegesAvions = (List<SiegeAvion>) request.getAttribute("siegesAvion");
    Avion avion = (Avion) request.getAttribute("avion");
    Vol vol = (Vol) request.getAttribute("vol");
    List<OffreSiegeAvionVol> offresSiege = (List<OffreSiegeAvionVol>) request.getAttribute("offresSiege");
//    if (vol != null)
//        offresSiege = vol.getOffreSiegeAvionVols();
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="row g-3" action="/ticketing/vol/new" method="post">

    <input type="hidden" name="idVol" value="<%=request.getParameter("idVol")%>">
    <input type="hidden" name="idAvion" value="<%=request.getParameter("idAvion")%>">
    <input type="hidden" name="idVilleDepart" value="<%=request.getParameter("idVilleDepart")%>">
    <input type="hidden" name="idVilleArrivee" value="<%=request.getParameter("idVilleArrivee")%>">
    <input type="hidden" name="departVol" value="<%=request.getParameter("departVol")%>">
    <input type="hidden" name="arriveeVol" value="<%=request.getParameter("arriveeVol")%>">

    <h3>Information sur les prix et promotion</h3>
    <div class="mb-3 row">
        <label for="staticEmail" class="col-sm-2 col-form-label">Avion :</label>
        <div class="col-sm-10">
            <input type="text" readonly class="form-control-plaintext" id="staticEmail" value="<%= avion.getLibelle() %>">
        </div>
    </div>
<%--    <%=vol.getId()%>--%>
<%--    <%=offresSiege.isEmpty()%>--%>
<%--    <%=offresSiege.size()%>--%>
    <% if (offresSiege != null && !offresSiege.isEmpty()) { %>
        <% for (OffreSiegeAvionVol offre : offresSiege) { %>
            <div class="col-md-3">
                <input type="hidden" name="idOffres[]" value="<%=offre.getId()%>">
                <label for="validationCustomSiege" class="form-label"><%= offre.getIdSiegeAvion().getIdTypeSiege().getLibelle() %> : <%=offre.getIdSiegeAvion().getNombre()%> places</label>
                <div class="input-group has-validation">
                    <span class="input-group-text" id="basic-addon1">$</span>
                    <input type="hidden" class="form-control" value="<%= offre.getIdSiegeAvion().getId() %>" aria-describedby="inputGroupPrepend" required name="idSiegesAvion[]">
                    <input type="number" step="0.01" class="form-control" value="<%= offre.getPrix() %>" id="validationCustomSiege" aria-describedby="inputGroupPrepend" required name="prix[]">
                    <div class="invalid-feedback">
                        Please choose a username.
                    </div>
                </div>
            </div>
        <% } %>
    <% } else { %>
        <% for (SiegeAvion sa : siegesAvions) { %>
            <h5><%= sa.getIdTypeSiege().getLibelle() %> : <%=sa.getNombre()%> places</h5>
            <div class="row">
                <div class="col-md-4">
                    <input type="hidden" name="idOffres[]" value="-1">
                    <label for="validationCustomSiege" class="form-label">Prix</label>
                    <div class="input-group has-validation">
                        <span class="input-group-text" id="basic-addon1">$</span>
                        <input type="hidden" class="form-control" value="<%= sa.getId() %>" aria-describedby="inputGroupPrepend" required name="idSiegesAvion[]">
                        <input type="number" step="0.01" class="form-control" value="<%= "0" %>" id="validationCustomSiege" aria-describedby="inputGroupPrepend" required name="prix[]">
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="validationCustomSiege" class="form-label">Places en promotion</label>
                    <div class="input-group has-validation">
                        <input type="number" class="form-control" value="<%= "0" %>" id="validationCustomSiege" aria-describedby="inputGroupPrepend" required name="quantite[]">
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="validationCustomSiege" class="form-label">Valeur promotion</label>
                    <div class="input-group has-validation">
                        <span class="input-group-text" id="basic-addon2">%</span>
                        <input type="hidden" class="form-control">
                        <input type="number" step="0.01" class="form-control" value="<%= "0" %>" id="validationCustomSiege" aria-describedby="inputGroupPrepend" required name="pourc[]">
                    </div>
                </div>
            </div>
        <% } %>
    <% } %>

    <div class="col-12">
        <button class="btn btn-primary" type="submit">Valider formulaire</button>
    </div>

</form>

