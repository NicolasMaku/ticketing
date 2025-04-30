<%@ page import="itu.nicolas.ticketing.models.UserTicketing" %>
<%
  String pg = (String) request.getAttribute("page");
  UserTicketing user = (UserTicketing) session.getAttribute("user");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html style="width: 100%; overflow-x: hidden">
<head>
  <title>JSP - Hello World</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Ticketing</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse d-flex" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/ticketing/reservation/liste?idUser=<%=user.getId()%>">Mes reservations</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/ticketing/vol/multicritere-front">Vol</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/ticketing/deconnexion">Se deconnecter</a>
        </li>
      </ul>
    </div>
    <div class="d-flex">
      <%=user.getUsername()%>
    </div>
  </div>
</nav>
<div class="row justify-content-center">
  <div class="col-lg-9 py-5">
    <jsp:include page='<%= pg %>'/>
  </div>

</div>
</body>
</html>
<script>
  function toggleMenu() {
    const submenu = document.getElementById("submenu");
    const button = document.querySelector("button[aria-expanded]");

    if (submenu.classList.contains("hidden")) {
      submenu.classList.remove("hidden");
      button.setAttribute("aria-expanded", "true");
    } else {
      submenu.classList.add("hidden");
      button.setAttribute("aria-expanded", "false");
    }
  }
</script>
