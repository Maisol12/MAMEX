<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="head.jsp"/>
</head>
<body>
<div class="offcanvas offcanvas-end" tabindex="-1" id="cartOffcanvas" aria-labelledby="cartOffcanvasLabel" style="width: 300px;" data-bs-backdrop="false">
    <div class="offcanvas-header">
        <h5 id="cartOffcanvasLabel">Tu Carrito</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <div class="d-flex flex-column">
            <c:choose>
                <c:when test="${not empty sessionScope.cart.items}">
                    <c:forEach var="cartItem" items="${sessionScope.cart.items}">
                        <div class="d-flex align-items-center mb-3">
                            <c:if test="${not empty cartItem.item.base64Images}">
                                <c:set var="imageName"
                                       value="${cartItem.item.base64Images.keySet().iterator().next()}"/>
                                <img src="data:image/jpeg;base64,${cartItem.item.base64Images[imageName]}"
                                     class="card-img-top"
                                     alt="${cartItem.item.name}" style="height: 120px; width: 120px">
                            </c:if>
                            <div class="flex-grow-1 ms-2">
                                <h5>${cartItem.item.name}</h5>
                                <p class="mb-0">Cantidad: ${cartItem.quantity}</p>
                                <p class="mb-0">Precio: $${cartItem.item.unitPrice}</p>
                            </div>
                            <button onclick="removeFromCart(${cartItem.item.id})" class="btn btn-sm">
                                <i data-feather="trash-2" style="width: 20px; height: 20px"></i>
                            </button>
                        </div>
                    </c:forEach>
                    <div class="container w-100">
                        <form action="${pageContext.request.contextPath}/user/checkout" method="post">
                            <button type="submit" class="btn bg-dark text-white w-100" style="border-radius: 0">Pagar
                            </button>
                        </form>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="text-center">
                        <p>Tu carrito está vacío.</p>
                        <i data-feather="frown"></i>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<script>
    function removeFromCart(itemId) {
        console.log("Función removeFromCart invocada con ID:", itemId);
        fetch('/user/remove-from-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'itemId=' + itemId,
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Actualizar la UI aquí
                    updateCartUIAfterRemoval(data);
                } else {
                    console.error('Error al eliminar el ítem del carrito');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
            });
    }
    function updateCartUIAfterRemoval(data) {
        // Supongamos que 'data' tiene la siguiente estructura:
        // { success: true, cartTotal: 4, cartPrice: "$40.00" }

        // Elimina el ítem de la lista del carrito (esto depende de tu estructura HTML)
        const itemElement = document.getElementById('cartItem-' + data.itemId);
        if (itemElement) {
            itemElement.remove();
        }

        // Actualizar el número de ítems en el carrito
        document.getElementById('cartCount').textContent = data.cartTotal;

        // Actualizar el precio total del carrito
        document.getElementById('cartTotalPrice').textContent = data.cartPrice;
    }

</script>

<jsp:include page="footer.jsp"/>
</body>
</html>
