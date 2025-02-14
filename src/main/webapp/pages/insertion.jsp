<%@ page import="itu.nicolas.ticketing.models.Ville" %>
<%@ page import="java.util.List" %>
<%
    List<Ville> villes = (List<Ville>) request.getAttribute("villes");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Insertion de vol</h1>
<form class="row g-3 needs-validation" novalidate method="post" action="/ticketing/vol/new">
    <div class="col-md-6">
        <label for="validationCustom01" class="form-label">Ville départ</label>
        <select class="form-select" id="validationCustom01" required name="idVilleDepart">
            <option selected disabled value="">Choisissez une ville</option>
            <% for(Ville v : villes) { %>
                <option value="<%=v.getId()%>"><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-6">
        <label for="validationCustom02" class="form-label">Ville arrivé</label>
        <select class="form-select" id="validationCustom02" required name="idVilleArrivee">
            <option selected disabled value="">Choisissez une ville</option>
            <% for(Ville v : villes) { %>
            <option value="<%=v.getId()%>"><%=v.getNom()%></option>
            <% } %>
        </select>
    </div>
    <div class="col-md-6">
        <label for="validationCustomUsername" class="form-label">Date depart</label>
        <div class="input-group has-validation">
            <span class="input-group-text" id="inputGroupPrepend">@</span>
            <input type="datetime-local" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" required name="departVol">
            <div class="invalid-feedback">
                Please choose a username.
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label for="validationCustom03" class="form-label">Date arrivee</label>
        <input type="datetime-local" class="form-control" id="validationCustom03" required name="arriveeVol">
        <div class="invalid-feedback">
            Please provide a valid city.
        </div>
    </div>

    <div class="col-12">
        <button class="btn btn-primary" type="submit">Submit form</button>
    </div>
</form>
