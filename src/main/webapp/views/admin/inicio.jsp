<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Inicio</title>
</head>

<body>

<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-fluid custom-container">
        <div class="container pt-4">
            <div class="cards d-grid justify-content-between">
                <div class="card-box">
                    <div class="container">
                        <div class="circle ms-2 mt-3"><i data-feather="users"></i></div>
                    </div>
                    <div class="container d-flex flex-column">
                        <span style="font-size: 14px;">Usuarios</span>
                        <span class="fw-bolder fs-4"><c:out value="${totalUsersCount}" /></span>
                    </div>
                </div>
                <div class="card-box">
                    <div class="container">
                        <div class="circle ms-2 mt-3"><i data-feather="dollar-sign"></i></div>
                    </div>
                    <div class="container d-flex flex-column">
                        <span style="font-size: 14px;">Ventas del mes</span>
                        <span class="fw-bolder fs-4"><c:out value="${totalEarnings}" /></span>
                    </div>
                </div>
                <div class="card-box">
                    <div class="container">
                        <div class="circle ms-2 mt-3"><i data-feather="user-check"></i></div>
                    </div>
                    <div class="container d-flex flex-column">
                        <span style="font-size: 14px;">Usuarios activos</span>
                        <span class="fw-bolder fs-4"><c:out value="${activeUsersCount}" /></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>


<jsp:include page="../../layouts/footer.jsp"/>

<script>
    let currentPath = window.location.pathname;
    let action = new URL(window.location.href).searchParams.get("action");
    let paramResult = new URL(window.location.href).searchParams.get("result");
    let paramMessage = new URL(window.location.href).searchParams.get("message");

    if (currentPath === "/user/mamex" && paramResult) {
        if (action === "login" && paramResult === "true") {
            alertify.success("Inicio de sesión exitoso!");
        } else if (action === "logout" && paramResult === "true") {
            alertify.success("Sesión cerrada exitosamente!");
        } else if (paramResult === "false") {
            alertify.error(paramMessage || "Ocurrió un error");
        }
    } else if (currentPath === "/user/admin/dashboard" && paramResult) {
        if (paramResult === "true") {
            alertify.success("Bienvenido admin.");
        }
    }
</script>

</body>
</html>