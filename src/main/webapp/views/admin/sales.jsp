<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Ventas</title>
    <script>
        function togglePayment(saleId) {
            const url = '/user/updatePaymentStatus';
            const data = {
                saleId: saleId,
                paymentStatus: true // Simplemente marcamos como confirmado
            };

            const button = event.target;
            const row = button.closest('tr');
            const estadoCell = row.querySelector('td:nth-child(4)');

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success") {
                        estadoCell.textContent = "Pagado";
                        row.style.backgroundColor = 'lightgreen';
                        setTimeout(() => {
                            row.style.backgroundColor = '';
                        }, 1000);
                    } else {
                        row.style.backgroundColor = 'lightcoral';
                        setTimeout(() => {
                            row.style.backgroundColor = '';
                        }, 1000);
                        alert("Hubo un error al actualizar el estado de pago");
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert("Error al comunicarse con el servidor: " + error.message);
                });
        }
    </script>
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
                                    <td><fmt:formatDate value="${sale.slDateCreate}" pattern="yyyy-MM-dd" /></td>
                                    <td><fmt:formatDate value="${sale.slDateUpdate}" pattern="yyyy-MM-dd" /></td>
                                    <td>${sale.numberSale}</td>
                                    <td>${sale.user.id}</td>
                                    <td>${sale.user.fullName}</td>
                                    <td>
                                        <button class="btn btn-outline-dark" onclick="togglePayment(${sale.idSale})">Confirmar</button>
                                    </td>

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