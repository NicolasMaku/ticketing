<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    List<Avion> avions = (List<Avion>) request.getAttribute("avions");
    Vol vol = (Vol) request.getAttribute("vol");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Insertion de vol</h1>
<form class="row g-3 needs-validation" novalidate method="post" action="/ticketing/vol/formPrix">
    <h3>Information sur le vol</h3>
    <input type="hidden" name="idVol" value="<%= (vol != null) ? vol.getId() : -1 %>">
    <div class="col-md-4">
        <label for="validationCustom00" class="form-label">Avion</label>
        <select class="form-select" id="validationCustom00" required name="idAvion">
            <option selected disabled value="">Choisissez un avion</option>
            <% for(Avion a: avions) { %>
              <option value="<%=a.getId()%>" <%= (vol != null && Objects.equals(vol.getIdAvion().getId(), a.getId())) ? "selected" : "" %> ><%=a.getLibelle()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-4">
        <label for="validationCustom01" class="form-label">Ville départ</label>
        <select class="form-select" id="validationCustom01" required name="idVilleDepart">
            <option selected disabled value="">Choisissez une ville</option>
            <% for(Ville v : villes) { %>
                <option value="<%=v.getId()%>" <%= (vol != null && Objects.equals(vol.getIdVilleDepart().getId(), v.getId())) ? "selected" : "" %> ><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-4">
        <label for="validationCustom02" class="form-label">Ville arrivé</label>
        <select class="form-select" id="validationCustom02" required name="idVilleArrivee">
            <option selected disabled value="">Choisissez une ville</option>
            <% for(Ville v : villes) { %>
            <option value="<%=v.getId()%>" <%= (vol != null && Objects.equals(vol.getIdVilleArrivee().getId(), v.getId())) ? "selected" : "" %> ><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-6">
        <label for="validationCustomUsername" class="form-label">Date depart</label>
        <div class="input-group has-validation">
            <input type="datetime-local" class="form-control" value="<%= (vol != null) ? vol.getDepartVol() : "" %>" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required name="departVol">
            <div class="invalid-feedback">
                Please choose a username.
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label for="validationCustom03" class="form-label">Date arrivee</label>
        <input type="datetime-local" class="form-control" value="<%= (vol != null) ? vol.getArriveeVol() : "" %>" id="validationCustom03" required name="arriveeVol">
        <div class="invalid-feedback">
            Please provide a valid city.
        </div>
    </div>

    <div class="col-12">
        <button class="btn btn-primary" type="submit">Valider formulaire</button>
    </div>

</form>

