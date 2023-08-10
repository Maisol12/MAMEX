<%--
  Created by IntelliJ IDEA.
  User: albertovazquez
  Date: 11/07/23
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <jsp:include page="head.jsp"/>
</head>
<body>
<jsp:include page="carrito.jsp"/>
<nav class="navbar sticky-top navbar-expand-lg bg-dark" data-bs-theme="dark">
    <div class="container d-flex justify-content-between">
        <a class="navbar-brand ms-lg-5 d-lg-block d-none"
           href="${pageContext.request.contextPath}/user/mamex"><img
                src="${pageContext.request.contextPath}/assets/img/OIP.jpg" alt="logo"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse text-center order-lg-1" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <ul class="mt-lg-3 d-lg-flex justify-content-lg-center justify-content-center list-unstyled">
                    <li><a href="${pageContext.request.contextPath}/user/mamex" class="nav-link">Inicio</a></li>
                    <li><a href="" class="nav-link">Categorias</a></li>
                    <li><a href="${pageContext.request.contextPath}/user/novedades" class="nav-link">Novedades</a></li>
                    <li><a href="${pageContext.request.contextPath}/user/contacto" class="nav-link">Contacto</a></li>
                    <li><a href="${pageContext.request.contextPath}/user/AboutUs" class="nav-link">Nosotros</a></li>
                </ul>
            </div>
        </div>
        <ul class="list-unstyled d-flex m-2 order-lg-2 text-center">
            <li>
                <button class="btn icon-button">
                    <i class="icon" data-feather="search"></i>
                </button>
            </li>
            <li>
                <div class="btn-group">
                    <button type="button" class="btn" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="icon" data-feather="user"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/login">Mi perfil</a>
                        </li>
                        <li><a class="dropdown-item" href="#">Menu item</a></li>
                        <li>
                            <div class="container text-center dropdown-item">
                                <form novalidate action="${pageContext.request.contextPath}/user/logout"
                                      method="get">
                                    <button class="btn bg-dark m-1" style="color: white;">Cerrar sesión</button>
                                </form>
                            </div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <button type="button" class="btn icon-button" data-bs-toggle="offcanvas"
                        data-bs-target="#cartOffcanvas">
                    <i class="icon" data-feather="shopping-cart"></i>
                </button>
            </li>
        </ul>
    </div>

</nav>


<jsp:include page="${pageContext.request.contextPath}footer.jsp"/>

</body>
</html>