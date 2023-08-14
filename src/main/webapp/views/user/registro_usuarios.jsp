<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/head.jsp"/>
    <title>Reviews</title>
    <style>

        .input-group img {

            width: 40px;
            height: 40px;
        }

        .input-group button:focus {
            border: none;
        }

        .rating input {
            display: none;
        }

<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-7 col-12 mt-lg-4 mt-md-5 mt-xs-1">
            <h1 class="display-3 text-center fs-1 fw-bold mt-5">
                CREAR CUENTA
            </h1>
            <div class="container">
                <form id="register-form" class="text-center w-75 m-auto pt-3" novalidate
                      action="${pageContext.request.contextPath}/user/register"
                      method="post">
                    <div class="form-floating m-4">
                        <input class="form-control" type="text" name="names" id="names" placeholder="Nombre(s)"
                               required>
                        <label>Nombre(s)</label>
                    </div>
                    <div class="form-floating m-4">
                        <input class="form-control" type="text" name="lastnames" id="lastnames" placeholder="Apellidos"
                               required>
                        <label>Apellidos</label>
                    </div>
                    <div class="form-floating m-4">
                        <input class="form-control" type="email" name="email" id="email" placeholder="E-mail" required>
                        <label>E-mail</label>
                    </div>
                    <div class="form-floating input-group m-4">
                        <input class="form-control" type="password" name="password" id="password"
                               placeholder="Contraseña (mayor a 6 dígitos)" required>
                        <span id="addon-wrapping">
                            <button type="button" class="btn btn-sm m-0 p-0" onclick="togglePasswordVisibility()">
                                <img class="m-0 p-0" id="eye" src="../../assets/svgs/eye-svgrepo-com.svg"
                                     alt="Icono mostrar contraseña" style="display:none;">
                                <img class="m-0 p-0" id="eye-off" src="../../assets/svgs/eye-slash-svgrepo-com.svg"
                                     alt="Icono ocultar contraseña">
                            </button>
                        </span>
                        <label>Contraseña (mayor a 6 dígitos)</label>
                    </div>


                    <button id="btn-registro" type="submit" class="btn btn-dark m-4"
                            style="font-weight: 500; border-radius: 0px; padding: 10px; width: 150px;">Regístrate
                    </button>
                </form>

    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <!-- List existing reviews -->
        <div class="col-12 col-md-6">
            <table class="table table-responsive table-striped">
                <h6 class="mb-3">Reseñas de nuestros clientes</h6>
                <thead>
                <tr>
                    <th>Usuario</th>
                    <th>Calificación</th>
                    <th>Comentarios</th>
                    <th>Fecha de reseña</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${reviews}" var="review">
                    <tr>
                        <td><c:out value="${review.name_user}" /></td>
                        <td><c:out value="${review.evaluacion}" /></td>
                        <td><c:out value="${review.comentario}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Form to submit a new review -->
        <div class="col-12 col-md-6 ">
            <h6 class="mt-5 mb-3">Tu reseña sobre ${item.name}</h6>
            <form action="${pageContext.request.contextPath}/user/review-product" method="post" class="mb-5">
                <input type="hidden" name="productId" value="${productId}" />
                <div class="mb-3">
                    <div class="row">
                        <div class="col-12 col-md-6">
                            <label class="form-label">¿Qué calificación le darías al producto?</label>
                            <div class="rating">
                                <input value="5" name="rating" id="star5" type="radio">
                                <label for="star5"></label>
                                <input value="4" name="rating" id="star4" type="radio">
                                <label for="star4"></label>
                                <input value="3" name="rating" id="star3" type="radio">
                                <label for="star3"></label>
                                <input value="2" name="rating" id="star2" type="radio">
                                <label for="star2"></label>
                                <input value="1" name="rating" id="star1" type="radio">
                                <label for="star1"></label>
                            </div>
                            <input type="hidden" name="rating" id="rating" value="5" />
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="comment" class="form-label">Comentarios acerca del producto:</label>
                    <textarea id="comment" name="comment" class="form-control" rows="2" required placeholder="E.g., Excelente producto, lo recomendaría..."></textarea>
                </div>

                <div>
                    <input type="submit" value="Publicar" class="btn btn-submit btn-sm btn-outline-dark" />
                </div>
            </form>

        </div>
    </div>
</div>
<script>
    function togglePasswordVisibility() {
        const passwordInput = document.getElementById("password");
        const eyeIcon = document.getElementById("eye");
        const eyeOffIcon = document.getElementById("eye-off");

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            eyeIcon.style.display = "";
            eyeOffIcon.style.display = "none";
        } else {
            passwordInput.type = "password";
            eyeIcon.style.display = "none";
            eyeOffIcon.style.display = "";
        }
    }


    (function () {
        const form = document.getElementById("register-form");
        form.addEventListener("submit", function (event) {
            if (!form.checkValidity()) { //Valida que los campos oligatorios esten correctamente llenados.
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add("was-validated");
        }, false);
    })();
</script>
</body>
</html>