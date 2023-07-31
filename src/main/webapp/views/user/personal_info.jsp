<%--
  Created by IntelliJ IDEA.
  User: albertovd
  Date: 18/07/23
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <jsp:include page="../../layouts/head.jsp"/>
    <title>Mi perfil</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<main>
    <div class="container mt-3 custom-container">
        <div class="row">
            <div class="col-lg-12">
                <div class="text-center mb-2">
                    <h2>Datos personales</h2>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="d-flex flex-column justify-content-center align-items-center">
                    <div class="card box-shadow-1 profile-card" style="border-radius: 0">
                        <div class="card-title text-center"><img class="profile-img" src="../../assets/img/OIP.jpg"
                                                                 alt="profile picture"></div>
                        <div class="card-body">
                            <div class="container">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Nombre</label>
                                            <input type="text" class="form-control" id="name" name="name"
                                                   value="${user.name_user}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="mb-3">
                                            <label for="lastName" class="form-label">Apellidos</label>
                                            <input type="text" class="form-control" id="lastName" name="lastName"
                                                   value="${user.lastnames}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="mb-3">
                                            <label for="email" class="form-label">Correo electrónico</label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                   value="${user.email}" disabled>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="mb-3">
                                            <label for="phone" class="form-label">Teléfono</label>
                                            <input type="text" class="form-control" id="phone" name="phone"
                                                   value="${user.phone}" disabled>
                                        </div>
                                    </div>
                                </div>
                                <form action="" class="text-end mb-0">
                                    <a href="${pageContext.request.contextPath}/user/update_view">
                                        <button class="btn btn-outline-dark m-2"><small>Editar </small><i
                                                class="custom-icon" data-feather="arrow-right"></i></button>
                                    </a>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
