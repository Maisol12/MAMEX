<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <jsp:include page="../../layouts/head.jsp"/>
    <style>
        .input-group img {

            width: 40px;
            height: 40px;
        }

        .input-group button:focus {
            border: none;
        }
    </style>
    <title>Manos Mexicanas</title>
</head>
<body>

<jsp:include page="../../layouts/nav.jsp"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-7 col-12 mt-lg-4 mt-md-1 ">
            <h1 class="display-3 text-center fs-1 fw-bold mt-5">
                INICIAR SESIÓN
            </h1>

            <div class="container-fluid">
                <form class="text-center m-auto pt-4 w-75" novalidate action="${pageContext.request.contextPath}/user/login" method="post">
                    <div class="form-floating m-4">
                        <input class="form-control" type="email" id="email" name="email" placeholder="E-mail" required>
                        <label>E-mail</label>
                    </div>
                    <div class="form-floating input-group m-4">
                        <input class="form-control" type="password" name="password" id="password"
                               placeholder="Contraseña" required>
                        <span id="addon-wrapping">
                            <button type="button" class="btn btn-sm m-0 p-0" onclick="togglePasswordVisibility()">
                                <img class="m-0 p-0" id="eye" src="../../assets/svgs/eye-svgrepo-com.svg"
                                     alt="Icono mostrar contraseña" style="display:none;">
                                <img class="m-0 p-0" id="eye-off" src="../../assets/svgs/eye-slash-svgrepo-com.svg"
                                     alt="Icono ocultar contraseña">
                            </button>
                        </span>
                        <label>Contraseña</label>
                    </div>
                    <a class="text-decoration-none text-dark link-animation"><button id="btnLogin" type="submit" class="btn btn-dark m-4"
                                                                                     style="font-weight: 500; border-radius: 0px; padding: 10px; width: 150px;">Iniciar
                        sesión</button>
                    </a>
                </form>
                <div class="container">
                    <div class="col-12 text-center mt-5">
                        <p class="text-center">¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/user/register-view"
                                                                         class="text-decoration-none text-dark link-animation"
                                                                         style=" font-weight: 700; color:black">Regístrate</a></p>
                        <!-- Aquí se agrega el token al enlace y se especifica que abra el modal -->
                        <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#resetPassword" data-token="${param.token}">
                            Olvidé mi contraseña
                        </button>
                    </div>
                    <div class="modal fade" id="resetPassword" tabindex="-1"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="d-flex justify-content-end m-3">
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-header">
                                    <div class="container text-center">
                                        <h1 class="mb-4 fw-bolder">
                                            Recuperar contraseña
                                        </h1>
                                    </div>
                                </div>
                                <div class="modal-body">
                                    <div class="container text-center">
                                        <span class="text-center lh-md " style="font-size: 1rem;">Introduce la
                                            dirección de correo que usaste en el registro. <br> Te enviaremos un correo
                                            con instrucciones para restablecer tu contraseña.</span>
                                        <div class="mt-5">
                                            <form id="reset-password-form" action="${pageContext.request.contextPath}/user/reset_password" method="POST">
                                                <label class="fw-bold" style="font-size: 14px;">Introduce tu correo electrónico</label>
                                                <div class="p-2">
                                                    <input style="width: inherit; padding: 6px;" type="email" name="email" id="email_password" placeholder="Correo electrónico" required>
                                                </div>
                                                <!-- Incluir el token en el formulario como campo oculto -->
                                                <input type="hidden" name="token" value="${param.token}">
                                                <input type="hidden" name="email" value="${param.email}">
                                                <button type="submit" class="btn btn-dark m-5 m-lg-4"
                                                        style="font-weight: 500; border-radius: 50px; padding: 10px; width: 100px;">Enviar</button>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div>
                                            <p class="text-center pb-5 m-3 fw-bold"
                                               style="font-size: 1rem; font-weight: 400;">
                                                Si necesitas más ayuda, contacta con nosotros en <a
                                                    href="mailto:tiendamanosmexicanas1@gmail.com">tiendamanosmexicanas1@gmail.com</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-5 p-0 d-lg-block d-none">
            <img class="w-100 vh-100"  src="${pageContext.request.contextPath}/assets/img/image 28.png">
        </div>
    </div>
</div>




<jsp:include page="../../layouts/footer.jsp"/>

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
</script>

<% if (request.getParameter("result") != null && request.getParameter("result").equals("true")) { %>
<script>
    alertify.alert("¡Bienvenido a Manos Mexicanas!", "¡Gracias por registrarte! Ahora puedes iniciar sesión con tu correo electrónico y contraseña.").set('onok', function() {
        // Si deseas hacer algo después de que el usuario haga clic en "OK", coloca el código aquí. De lo contrario, simplemente puedes omitir el método set('onok', ...).
    });
</script>
<% } %>

<c:if test="${param.result == 'false'}">
    <script>
        alertify.error('Usuario o contraseña incorrectos');
    </script>
</c:if>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Agregar el valor del token al enlace del botón dentro del modal
        var tokenButtons = document.querySelectorAll("[data-bs-target='#resetPassword']");
        tokenButtons.forEach(function(button) {
            var token = button.getAttribute("data-token");
            var modalLink = button.querySelector("a");
            modalLink.href = "http://localhost:8080/views/user/password-recovery.jsp?token=" + token;
        });
    });
    function addTokenToResetForm() {
        var resetForm = document.getElementById("reset-password-form");
        var tokenInput = document.createElement("input");
        tokenInput.type = "hidden";
        tokenInput.name = "token";
        tokenInput.value = "${param.token}";
        resetForm.appendChild(tokenInput);
    }
</script>
<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
