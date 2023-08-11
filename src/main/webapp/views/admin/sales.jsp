<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Ventas</title>
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
                            <th scope="col">Cantidad</th>
                            <th scope="col">Subtotal</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Fecha Creación</th>
                            <th scope="col">Fecha Actualización</th>
                            <th scope="col">Número Venta</th>
                            <th scope="col">ID Usuario</th>
                            <th scope="col">Nombre usuario</th>
                            <th scope="col">Pago Confirmado</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sale" items="${sales}">
                            <tr>
                                <th scope="row">${sale.idSale}</th>
                                <td>${sale.quantitySale}</td>
                                <td>$${sale.subtotal}</td>
                                <td>${sale.saleState}</td>
                                <td>${sale.slDateCreate}</td>
                                <td>${sale.slDateUpdate}</td>
                                <td>${sale.numberSale}</td>
                                <td>${sale.user.id}</td>
                                <td>${sale.user.fullName}</td>
                                <td>
                                    <input type="checkbox" name="pagoConfirmado" value="${sale.idSale}" ${sale.pagoConfirmado ? 'checked' : ''} />
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
