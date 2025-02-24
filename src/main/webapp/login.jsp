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

  <!-- Tailwind CSS via CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">

<div class="bg-white p-8 rounded-lg shadow-md w-96">
  <%  if (errors != null) { %>
    <p class="text-red-500">Erreur</p>
  <% } %>
  <%  if (formData != null) { %>
  <h2 class="text-2xl font-bold text-center text-gray-700 mb-4">Erreur</h2>
  <% } %>
  <h2 class="text-2xl font-bold text-center text-gray-700 mb-4">Connexion</h2>

  <% if (request.getAttribute("bad-validation") != null) { %>
    <p>Erreur existant</p>
    <% for (Map.Entry<String, String> entry : errors.entrySet() ) { %>
      <p><%= entry.getKey() %> : <%= entry.getValue() %></p>
    <% } %>
  <% } %>

  <form action="/ticketing/login/traitement" method="post">
    <!-- Champ Email -->
    <div class="mb-4">
      <label class="block text-gray-600 text-sm font-medium mb-1" for="email">Email</label>
      <input type="email" id="email" name="user.email"
             class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>

    <!-- Champ Mot de passe -->
    <div class="mb-4">
      <label class="block text-gray-600 text-sm font-medium mb-1" for="password">Mot de passe</label>
      <input type="password" id="password" name="user.password" class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>

    <!-- Bouton de connexion -->
    <button type="submit"
            class="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 transition">
      Se connecter
    </button>
  </form>

  <!-- Lien d'inscription -->
  <p class="text-sm text-gray-500 text-center mt-4">
    Pas encore de compte ? <a href="" class="text-blue-500 hover:underline">Inscrivez-vous</a>
  </p>
</div>
</body>
</html>
