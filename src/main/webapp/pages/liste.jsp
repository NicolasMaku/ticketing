<%@ page import="itu.nicolas.ticketing.models.Vol" %>
<%@ page import="java.util.List" %><%
    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Gerer les vols</h1>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Ville de depart</th>
        <th scope="col">Déstination</th>
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
            <td><%=v.getDepartVol()%></td>
            <td><%=v.getArriveeVol()%></td>
            <td>
                <a type="button" href="/ticketing/vol" class="btn btn-primary">Modifier</a>
                <a type="button" href="/ticketing/vol/delete?id=<%=v.getId()%>" class="btn btn-secondary">Supprimer</a>
                <a class="btn btn-primary btn-sm">
                    <i class="bi bi-pencil"></i> Modifier
                </a>

                <a class="btn btn-danger btn-sm">
                    <i class="bi bi-trash"></i> Supprimer
                </a>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>