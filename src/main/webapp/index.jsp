<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <jsp:include page="layouts/head.jsp"/>
    <title>Manos Mexicanas</title>
</head>

<body>
<jsp:include page="/layouts/nav.jsp"/>

<div class="container-fluid p-0 m-0">
    <div id="carouselExampleAutoplaying" class="carousel slide micarrusel w-100" data-bs-ride="carousel"
         data-bs-interval="3000">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/assets/img/h5.png" class="d-block w-100"
                     alt="Promociones en mamex">
            </div>
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/assets/img/h1.png" class="d-block w-100"
                     alt="Promociones en mamex">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/assets/img/h2.png" class="d-block w-100"
                     alt="Promociones en mamex">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/assets/img/h3.png" class="d-block w-100"
                     alt="Promociones en mamex">
            </div>
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/assets/img/h4.png" class="d-block w-100"
                     alt="Promociones en mamex">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>
<section class="pt-5 pb-5">
    <div class="container">
        <div class="row">
            <div class="col-6">
                <h3 class="mb-3">Nuevos productos</h3>
            </div>
            <div class="col-12">
                <div id="carouselExampleIndicators2" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner"> <!-- Aquí está el contenedor principal del carrusel -->
                        <c:forEach var="item" items="${items}" varStatus="loop">
                            <!-- Abre un nuevo ítem del carrusel cada 3 productos o si es el primer producto -->
                            <c:if test="${loop.index % 3 == 0}">
                                <div class="carousel-item${loop.index == 0 ? ' active' : ''}">
                                <div class="row">
                            </c:if>

                            <!-- Muestra la tarjeta del producto -->
                            <div class="col-md-4 mb-3">
                                <a href="${pageContext.request.contextPath}/user/productDetails?id=${item.id}" class="text-decoration-none text-black-50">
                                    <div class="card">
                                        <!-- ... contenido de tu tarjeta ... -->
                                        <div class="box-shadow-1">
                                            <!-- Imagen del producto -->
                                            <c:if test="${not empty item.base64Images}">
                                                <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}"/>
                                                <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top" alt="${item.name}">
                                            </c:if>
                                            <div class="card-body">
                                                <h6 class="card-title">${item.name}</h6>
                                                <p class="fw-lighter card-text">$${item.unitPrice}</p>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>

                            <!-- Cierra el ítem del carrusel cada 3 productos o si es el último producto -->
                            <c:if test="${(loop.index + 1) % 3 == 0 || loop.index==9}">
                                </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div> <!-- Fin del contenedor principal del carrusel -->
                    <!-- Botones de navegación -->
                    <a class="carousel-control-prev" href="#carouselExampleIndicators2" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Anterior</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators2" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Siguiente</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</section>

<aside>
    <div class="container-fluid p-5">
        <div class="container p-lg-5">
            <section class="container">
                <div class="row">
                    <article class="col-12 col-lg-7">
                        <h2 class="display-4 mb-5 mt-5">Descubre la belleza en cada hilo</h2>
                        <p class="lead text-sm-start text-md-start">Sumérgete en un mundo de
                            posibilidades con nuestra amplia
                            colección de tejidos de alta calidad
                            cuidadosamente seleccionados para que puedas crear prendas únicas y
                            proyectos creativos que reflejen tu estilo y pasión por la moda.</p>
                        <a href="${pageContext.request.contextPath}/user/register-view"
                           style="text-decoration: none; color: whitesmoke;">
                            <button class="btn btn-dark m-4"
                                    style="font-weight: 300; border-radius: 0; padding: 10px; width: 150px;">Regístrate
                            </button>
                        </a>
                    </article>
                    <article class="col-12 col-lg-5 ps-lg-5 ps-sm-0">
                        <img src="${pageContext.request.contextPath}/assets/img/e9be2a1f1471feb0c1b18083309ea295.jpg"
                             alt="img-hilos" class="img-fluid">
                    </article>
                </div>
            </section>
        </div>
    </div>
</aside>

<footer class="p-5" style="background-color: #6b6b6b">
    <div class="container text-white border-bottom border-white">
        <nav>
            <h1 style="font-weight: 400">
                MANOS <br/>
                MEXICANAS
            </h1>
            <br/><br/>
            <p>
                <a href="mailto:manos_mexicanas@gmail.com"
                   class="link-light link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">manos_mexicanas@gmail.com</a>
            </p>
            <br/><br/>
            <p>Llama al: 777-549-9809</p>
            <br/>

        </nav>
    </div>
    <p class="text-white text-center p-5" style="font-family: 'Raleway', sans-serif;">
        Copyrights. All rights reserved.
    </p>
</footer>

<jsp:include page="layouts/footer.jsp"/>

<script>
    let currentPath = window.location.pathname;
    let action = new URL(window.location.href).searchParams.get("action");
    let paramResult = new URL(window.location.href).searchParams.get("result");
    let paramMessage = new URL(window.location.href).searchParams.get("message");
    let paramUsername = new URL(window.location.href).searchParams.get("username");

    if (currentPath === "/user/mamex" && paramResult) {
        if (action === "login" && paramResult === "true") {
            alertify.set('notifier', 'position', 'top-right');
            alertify.success("¡Bienvenido!");
        } else if (action === "logout") {
            alertify.set('notifier', 'position', 'top-right');
            alertify.success(`Sesión cerrada exitosamente.`);
        } else if (paramResult === "false") {
            alertify.set('notifier', 'position', 'top-right');
            alertify.error("Ocurrió un error");
        }
    } else if (currentPath === "/user/admin/dashboard" && paramResult) {
        if (paramResult === "true") {
            alertify.set('notifier', 'position', 'top-right');
            alertify.success("Bienvenido admin.");
        }
    }
</script>

</body>
</html>