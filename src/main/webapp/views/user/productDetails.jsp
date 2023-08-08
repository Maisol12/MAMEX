<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
                <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}" />
                <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="img-fluid product-img" alt="${item.name}">
            </c:if>
        </div>

        <!-- Detalles del producto -->
        <div class="col-md-4">
            <h3 class="display-6">${item.name}</h3>
            <p class="lead">${item.description}</p>
            <p class="lead">$${item.unitPrice}</p>
            <button type="submit" class="btn w-100 btn-outline-dark" onclick="addToCart(${item.id})" style="border-radius: 0"><span>Agregar al carrito</span></button>
        </div>
    </div>

    <!-- Comentarios de los usuarios -->
    <div class="row mt-5">
        <div class="col-12">
            <h5 class="display-6">Comentarios de los usuarios</h5>
            <c:forEach var="comment" items="${comments}">
                <div class="card">
                    <h5 class="card-title">${comment.user}</h5>
                    <p class="card-text">${comment.text}</p>
                </div>
            </c:forEach>
        </div>
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
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
</script>
<jsp:include page="../../layouts/footer.jsp"/>


</body>
</html>
