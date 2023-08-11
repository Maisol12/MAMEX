<%--
  Created by IntelliJ IDEA.
  User: aldair.py
  Date: 09/08/23
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <jsp:include page="../../layouts/head.jsp"/>
    <title>Editar perfil</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<main>
    <div class="container mt-3 custom-container">
        <!-- Personal info edit -->
        <div class="row">
            <div class="col-lg-12">
                <div class="text-center mb-2">
                    <h2>Editar datos personales</h2>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="d-flex flex-column justify-content-center align-items-center">
                    <div class="card box-shadow-1 profile-card" style="border-radius: 0">
                        <form action="${pageContext.request.contextPath}/user/update" method="post" enctype="multipart/form-data">
                            <div class="card-title text-center">
                                <img src="data:image/jpeg;base64,${base64Image}" alt="User Image" style="max-width: 200px; height: auto;">
                                <!-- Upload new profile picture -->
                                <div class="mt-2">
                                    <label for="profilePicUpload">Cambiar foto de perfil</label>
                                    <input type="file" id="profilePicUpload" name="profilePic">
                                </div>
                            </div>

                            <div class="card-body">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <div class="mb-3">
                                                <label for="names" class="form-label">Nombre</label>
                                                <input type="text" class="form-control" id="names" name="names" value="${user.names}">

                                            </div>
                                        </div>
                                        <div class="col-lg-6">
                                            <div class="mb-3">
                                                <label for="lastnames" class="form-label">Apellidos</label>
                                                <input type="text" class="form-control" id="lastnames" name="lastnames" value="${user.lastnames}">
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" name="userId" value="${user.id}">
                                    <!-- Save Changes button -->
                                    <div class="text-end mb-0">
                                        <button type="submit" class="btn btn-outline-dark m-2">Guardar cambios</button>

                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
