<%--
  Created by IntelliJ IDEA.
  User: albertovazquez
  Date: 11/07/23
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <jsp:include page="headAdmin.jsp"/>
</head>
<body>
<nav class="navbar navbar-expand-lg sticky-top sticky-sm-bottom bg-light">
    <div class="container">
        <a class="navbar-brand ms-lg-5">
            <img src="${pageContext.request.contextPath}/assets/img/OIP.jpg" alt="LOGO MAMEX" style="width: 36px; height: 36px; border-radius: 50%" class="d-lg-block d-none">
            <!-- Botón para abrir el offcanvas en pantallas pequeñas -->
            <button class="btn btn-small btn-outline-dark ms-4 d-lg-none" type="button" data-bs-toggle="offcanvas"
                    data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
                <i data-feather="sidebar" style="width: 20px; height: 20px"></i>
            </button>
        </a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/inicio">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders">Ordenes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/products">Productos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/sales">Ventas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Usuarios</a>
                </li>
            </ul>
        </div>


        <form novalidate action="${pageContext.request.contextPath}/user/logout"
              method="get">
            <button class="btn btn-small btn-outline-dark" style="border-radius: 50px"><small>Salir</small> <i data-feather="log-out" style="width: 16px; height: 16px"></i></button>
        </form>


    </div>
</nav>

<!-- Offcanvas -->
<div class="offcanvas offcanvas-start" style="width: 270px;" id="offcanvasExample"
     aria-labelledby="offcanvasExampleLabel">
    <div class="offcanvas-header">
        <h6 class="offcanvas-title" id="offcanvasExampleLabel">Menú</h6>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
    </div>
    <div class="offcanvas-body">
        <div class="nav">
            <div class="container m-5">
                <ul class="navbar-nav">
                    <li class="nav-link mb-5">
                        <div class="row"><a class="nav-link" href="${pageContext.request.contextPath}/admin/inicio"><i
                                data-feather="home"></i> Inicio</a>
                        </div>
                    </li>
                    <li class="nav-item mb-5">
                        <div class="row"><a class="nav-link" href="${pageContext.request.contextPath}/admin/orders"><i
                                data-feather="shopping-cart"></i> Ordenes</a></div>

                    </li>
                    <li class="nav-item mb-5">
                        <div class="row"><a class="nav-link" href="${pageContext.request.contextPath}/admin/products"><i
                                data-feather="tag"></i> Productos</a></div>
                    </li>
                    <li class="nav-item mb-5">
                        <div class="row"><a class="nav-link" href="${pageContext.request.contextPath}/admin/sales"><i
                                data-feather="dollar-sign"></i> Ventas</a></div>
                    </li>
                    <li class="nav-item mb-5">
                        <div class="row"><a class="nav-link" href="${pageContext.request.contextPath}/admin/users"><i
                                data-feather="users"></i> Usuarios</a></div>
                    </li>
                </ul>

            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>