<%@ page import="itu.nicolas.ticketing.models.ConfigurationLimite" %>
<%@ page import="java.util.List" %><%
    List<ConfigurationLimite> configs = (List<ConfigurationLimite>) request.getAttribute("configs");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="row g-3 needs-validation" novalidate method="post" action="/ticketing/configurations/traitement">
    <h3>Modification configuration</h3>
    <% for (ConfigurationLimite cl : configs) { %>
        <div class="col-md-4">
            <label for="validationCustomUsername" class="form-label">Limite <%=cl.getLibelle()%></label>
            <div class="input-group has-validation">
                <span class="input-group-text" id="basic-addon1">Heures</span>
                <input type="hidden" name="idConfig[]" value="<%=cl.getId()%>">
                <input type="number" class="form-control" id="validationCustomUsername" aria-describedby="inputGroupPrepend" value="<%=cl.getNbreHeure()%>" name="heures[]">
                <div class="invalid-feedback">
                    Please choose a username.
                </div>
            </div>
        </div>
    <% } %>

    <div class="col-12">
        <button class="btn btn-primary" type="submit">Continuer</button>
    </div>

</form>