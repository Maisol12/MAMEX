<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/head.jsp"/>
    <title>Novedades</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container text-center">
    <div class="row">
        <div class="col-12">
            <h3 class="mt-4 mb-4">Lo más nuevo en <em>Manos Mexicanas</em></h3>
        </div>
    </div>
    <div class="row row-cols-1 row-cols-sm-1 row-cols-md-3 row-cols-lg-4 g-4 justify-content-center text-lg-start text-sm-center">
        <c:forEach var="item" items="${items}">
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/user/productDetails?id=${item.id}">
                    <div class="card custom-card h-100">
                        <c:if test="${not empty item.base64Images}">
                            <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}"/>
                            <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top"
                                 alt="${item.name}">
                        </c:if>
                        <div class="card-body">
                            <h6 class="card-title">${item.name}</h6>
                            <h6>$${item.unitPrice}</h6>
                            <small class="card-text fw-lighter" style="font-size: 14px;">${item.description}</small>
                        </div>
                        <div class="card-footer bg-light">
                            <div class="row row-cols-2 justify-content-around">
                                <button class="btn btn-sm btn-outline-warning" onclick="addToCart(${item.id})">
                                    <small class="text">Agregar <i data-feather="shopping-cart" style="width: 14px; height: 14px"></i></small>
                                </button>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>





<script>
    function addToCart(itemId) {
        fetch('/user/add-to-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'itemId=' + itemId + '&quantity=1',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                // Redirige al usuario al carrito después de agregar el ítem
                window.location.href = '/user/cart';
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
</script>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
