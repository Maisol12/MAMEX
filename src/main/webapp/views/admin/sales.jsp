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
        function togglePayment(saleId, isChecked) {
            // Definir la URL del servidor y los datos que se enviarán
            const url = '/user/updatePaymentStatus';
            const data = {
                saleId: saleId,
                paymentStatus: isChecked // true para "pagado", false para "pendiente"
            };

            // Hacer la solicitud al servidor usando la API Fetch
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
                        alert("Estado de pago actualizado con éxito");
                        // Recargar la página
                        location.reload();
                    } else {
                        alert("Hubo un error al actualizar el estado de pago");
                        // Recargar la página
                        location.reload();
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
                                    <input type="checkbox" name="pagoConfirmado" value="${sale.idSale}"
                                        ${sale.pagoConfirmado ? 'checked' : ''}
                                           onclick="togglePayment(${sale.idSale}, this.checked)" />
                                </td>
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
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>
