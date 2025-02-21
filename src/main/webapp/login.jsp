<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <%  if (request.getAttribute("erreur") != null) { %>
    <p class="text-red-500"><%= request.getAttribute("erreur") %></p>
  <% } %>
  <h2 class="text-2xl font-bold text-center text-gray-700 mb-4">Connexion</h2>

  <form action="/ticketing/login/traitement" method="post">
    <!-- Champ Email -->
    <div class="mb-4">
      <label class="block text-gray-600 text-sm font-medium mb-1" for="email">Email</label>
      <input type="email" id="email" name="email" required
             class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>

    <!-- Champ Mot de passe -->
    <div class="mb-4">
      <label class="block text-gray-600 text-sm font-medium mb-1" for="password">Mot de passe</label>
      <input type="password" id="password" name="mdp" required
             class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
    </div>

    <!-- Bouton de connexion -->
    <button type="submit"
            class="w-full bg-blue-500 text-white font-semibold py-2 px-4 rounded-lg hover:bg-blue-600 transition">
      Se connecter
    </button>
  </form>

  <!-- Lien d'inscription -->
  <p class="text-sm text-gray-500 text-center mt-4">
    Pas encore de compte ? <a href="register.jsp" class="text-blue-500 hover:underline">Inscrivez-vous</a>
  </p>
</div>
</body>
</html>
