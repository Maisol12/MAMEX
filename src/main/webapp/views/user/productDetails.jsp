<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://code.jquery.com/jquery-3.7.0.js" integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.x.x/dist/umd/popper.min.js"></script>
    <link rel="stylesheet" href="../../assets/css/bootstrap.min.css">
    <script src="../../assets/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="../../layouts/head.jsp"/>
    <title>${item.name}</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container mt-5">
    <div class="row">
        <!-- Imagen del producto -->
        <div class="col-md-8 mb-4">
            <c:if test="${not empty item.base64Images}">
                <c:forEach var="imageName" items="${item.base64Images.keySet()}">
                    <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="d-block w-100 mb-3" alt="${item.name}">
                </c:forEach>
            </c:if>
        </div>

        <!-- Detalles del producto -->
        <div class="col-md-4">
            <h4 class="display-6 pt-5">${item.name}</h4>
            <p class=" fw-light mt-5 mb-5">${item.description}</p>
            <p class="lead fw-lighter">$${item.unitPrice}</p>
            <button type="submit" class="btn w-100 btn-outline-dark" onclick="addToCart(${item.id})" style="border-radius: 0"><span>Agregar al carrito</span></button>
        </div>
    </div>
</div>

<jsp:include page="../../views/user/review_product.jsp"/>

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
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
</script>

<script src="../../assets/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>