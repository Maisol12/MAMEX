<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Usuarios</title>
</head>
<body>

<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-main">
        <div class="card text-end">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped caption-top">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Apellido</th>
                            <th scope="col">Email</th>
                            <th scope="col">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <th scope="row">${user.id}</th>
                                <td>${user.names}</td>
                                <td>${user.lastnames}</td>
                                <td>${user.email}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/user/delete" method="post">
                                        <input type="hidden" name="userId" value="${user.id}" />
                                        <button type="submit" class="btn btn-danger btn-sm btn-outline" onclick="return confirm('¿Estás seguro de que deseas eliminar a este usuario?');"><i data-feather="trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>
