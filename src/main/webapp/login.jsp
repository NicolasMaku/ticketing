<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Map<String, String> errors = (Map<String, String>) request.getAttribute("bad-validation");
  Map<String, String[]> formData = (Map<String, String[]>) request.getAttribute("formDataValidation");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body class="d-flex justify-content-center align-items-center vh-100 bg-light">

<div class="bg-white p-4 rounded shadow w-25">
  <% if (errors != null) { %>
  <p class="text-danger">Erreur</p>
  <% } %>
  <% if (formData != null) { %>
  <h2 class="text-center text-danger mb-3">Erreur</h2>
  <% } %>
  <h2 class="text-center text-primary mb-3">Connexion</h2>

  <% if (request.getAttribute("bad-validation") != null) { %>
  <p class="text-danger">Erreur existant</p>
  <% for (Map.Entry<String, String> entry : errors.entrySet()) { %>
  <p class="text-danger"><%= entry.getKey() %> : <%= entry.getValue() %></p>
  <% } %>
  <% } %>

  <form action="/ticketing/login/traitement" method="post">
    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" id="email" name="user.email" class="form-control">
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Mot de passe</label>
      <input type="password" id="password" name="user.password" class="form-control">
    </div>

    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
  </form>

  <p class="text-center mt-3">
    Pas encore de compte ? <a href="" class="text-primary">Inscrivez-vous</a>
  </p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
