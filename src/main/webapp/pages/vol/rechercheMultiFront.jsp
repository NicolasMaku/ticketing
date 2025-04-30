<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="itu.nicolas.ticketing.models.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
    List<Avion> avions = (List<Avion>) request.getAttribute("avions");
    Vol vol = (Vol) request.getAttribute("vol");

    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
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
<form class="row g-3 needs-validation" novalidate method="post" action="/ticketing/vol/traite-multi">
    <div>
        <h3>Liste des vols</h3>
        <a href="/ticketing/vol/export-csv" class="btn btn-outline-success">
            <i class="bi bi-file-earmark-spreadsheet"></i> Export en CSV
        </a>
    </div>
    <input type="hidden" name="idVol" value="<%= (vol != null) ? vol.getId() : -1 %>">
    <div class="col-md-4">
        <label for="validationCustom00" class="form-label">Avion</label>
        <select class="form-select" id="validationCustom00" required name="idAvion">
            <option selected value="-1">Tout</option>
            <% for(Avion a: avions) { %>
              <option value="<%=a.getId()%>" <%= (request.getParameter("idAvion") != null && Integer.parseInt(request.getParameter("idAvion")) == a.getId()) ? "selected" : "" %> ><%=a.getLibelle()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-4">
        <label for="validationCustom01" class="form-label">Ville départ</label>
        <select class="form-select" id="validationCustom01" required name="idVilleDepart">
            <option selected value="-1">Tout</option>
            <% for(Ville v : villes) { %>
                <option value="<%=v.getId()%>" <%= (request.getParameter("idVilleDepart") != null && Integer.parseInt(request.getParameter("idVilleDepart")) == v.getId()) ? "selected" : "" %> ><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-4">
        <label for="validationCustom02" class="form-label">Ville arrivé</label>
        <select class="form-select" id="validationCustom02" required name="idVilleArrivee">
            <option selected value="-1">Tout</option>
            <% for(Ville v : villes) { %>
            <option value="<%=v.getId()%>" <%= (request.getParameter("idVilleArrivee") != null && Integer.parseInt(request.getParameter("idVilleArrivee")) == v.getId()) ? "selected" : "" %> ><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-3">
        <label for="validationCustomUsername" class="form-label">Date depart min</label>
        <div class="input-group has-validation">
            <input type="datetime-local" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend"  value="<%= (request.getParameter("departVol") != null ) ? request.getParameter("departVol") : "" %>" name="departVol">
            <div class="invalid-feedback">
                Please choose a username.
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <label for="validationCustom03" class="form-label">Date max</label>
        <input type="datetime-local" class="form-control" id="validationCustom03" value="<%= (request.getParameter("arriveeVol") != null ) ? request.getParameter("arriveeVol") : "" %>" name="arriveeVol">
        <div class="invalid-feedback">
            Please provide a valid city.
        </div>
    </div>
    <div class="col-md-3">
        <label for="validationCustom03" class="form-label">Prix min</label>
        <div class="input-group has-validation">
            <span class="input-group-text" id="basic-addon1">$</span>
            <input type="number" step="0.01" class="form-control" id="validationCustomSiege" value="0" aria-describedby="inputGroupPrepend" required name="prixMin" value="<%= (request.getParameter("prixMin") != null ) ? request.getParameter("prixMin") : "" %>">
            <div class="invalid-feedback">
                Please choose a username.
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <label for="validationCustom03" class="form-label">Prix max</label>
        <div class="input-group has-validation">
            <span class="input-group-text" id="basic-addon1">$</span>
            <input type="number" step="0.01" class="form-control" id="validationCustomSiege" value="0" aria-describedby="inputGroupPrepend" required name="prixMax" value="<%= (request.getParameter("prixMax") != null ) ? request.getParameter("prixMax") : "" %>">
            <div class="invalid-feedback">
                Please choose a username.
            </div>
        </div>
    </div>

    <div class="col-12">
        <button class="btn btn-primary" type="submit">Rechercher</button>
    </div>

</form>

<div class="g-3 py-3">
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Ville de depart</th>
            <th scope="col">Déstination</th>
            <th scope="col">Avion</th>
            <th scope="col">Decollage</th>
            <th scope="col">Arrivée</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for(Vol v : vols) { %>
        <tr class="">
            <th scope="row"><%=v.getId()%></th>
            <td><%=v.getIdVilleDepart().getNom()%></td>
            <td><%=v.getIdVilleArrivee().getNom()%></td>
            <td><%=v.getIdAvion().getLibelle()%></td>
            <td><%=v.getDepartVol().format(formatter)%></td>
            <td><%=v.getArriveeVol().format(formatter)%></td>
            <td>
                <a href="/ticketing/reservation?id=<%=v.getId()%>" class="btn btn-primary btn-sm">
                    <i class="bi bi-pencil"></i> Reserver
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
