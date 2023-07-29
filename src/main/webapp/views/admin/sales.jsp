<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700&display=swap');

        body {
            font-family: 'Inter', sans-serif !important;
        }
    </style>
    <link rel="icon" href="../../assets/img/OIP.jpg">
    <link rel="stylesheet" href="../../assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../assets/css/styles.css">
    <title>Usuarios</title>
</head>
<body>
<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-main">
        <div class="card text-end">
            <div class="card-title pt-4 pe-3">
                <a style="text-decoration: none; color:black" href="/user/admin/create-sale">
                    <small>Crear Venta</small><button class="btn btn-success btn-sm btn-outline pt-2 ms-2"><i data-feather="plus"></i></button>
                </a>
            </div>
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
                            <th scope="col">ID Producto</th>
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
                                <td>${sale.fkIdUser}</td>
                                <td>${sale.fkIdItem}</td>
                                <td>
                                    <input type="checkbox" name="pagoConfirmado" value="${sale.idSale}" ${sale.pagoConfirmado ? 'checked' : ''} />
                                </td>
                                <td>
                                    <a href="./editar_venta.jsp?id=${sale.idSale}" style="text-decoration: none">
                                        <button class="btn btn-primary btn-sm btn-outline"><i data-feather="edit"></i></button>
                                    </a>
                                    <button class="btn btn-danger btn-sm btn-outline"><i data-feather="trash"></i></button>
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
