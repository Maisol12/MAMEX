<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Ordenes</title>
</head>
<body>
<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-fluid custom-container">
        <div class="container pt-4">
            <div class="card text-end">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped caption-top">
                            <thead>
                            <tr>
                                <th scope="col">ID Orden</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Fecha Creación</th>
                                <th scope="col">ID Usuario</th>
                                <th scope="col">ID Venta</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <th scope="row">${order.id}</th>
                                    <td>${order.state}</td>
                                    <td>${order.date}</td>
                                    <td>${order.fkIdUser}</td>
                                    <td>${order.fkIdSale}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>