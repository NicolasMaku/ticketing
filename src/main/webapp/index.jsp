<%
  String pg = (String) request.getAttribute("page");
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

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
          <a class="nav-link active" aria-current="page" href="#">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Features</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Pricing</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Vol
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
            <li><a class="dropdown-item" href="#">Inserer</a></li>
            <li><a class="dropdown-item" href="#">Gerer</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <div>
      <a class="nav-link" href="">A propés</a>
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
