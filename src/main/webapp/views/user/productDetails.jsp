<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <jsp:include page="../../layouts/head.jsp"/>
    <title>${item.name}</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container mt-5">
    <div class="row">
        <!-- Imagen del producto -->
        <div class="col-md-8">
            <c:if test="${not empty item.base64Images}">
                <div class="row row-cols-1">
                    <div class="col">
                        <img src="data:image/jpeg;base64,${item.base64Images['image1']}" class="img-fluid"
                             alt="${item.name}">
                    </div>
                </div>
                <div class="row row-cols-2 mt-3">
                    <div class="col-6">
                        <img src="data:image/jpeg;base64,${item.base64Images['image2']}" class="img-fluid"
                             alt="${item.name}">
                    </div>
                    <div class="col-6">
                        <img src="data:image/jpeg;base64,${item.base64Images['image3']}" class="img-fluid"
                             alt="${item.name}" style="max-width: 100%; max-height: 100%">
                    </div>
                </div>
            </c:if>
        </div>


        <!-- Detalles del producto -->
        <div class="col-md-4">
            <h4 class="display-6 pt-5">${item.name}</h4>
            <p class="fw-light mt-5 mb-5">${item.description}</p>
            <p class="fw-light mt-5 mb-5">Color: ${item.color}</p>
            <p class="fw-light mt-5 mb-5">Stock: ${item.stock}</p>
            <p class="lead fw-lighter">$${item.unitPrice}</p>
            <button type="submit" class="btn w-100 btn-outline-dark" onclick="addToCart(${item.id})"
                    style="border-radius: 0"><span>Agregar a la bolsa</span></button>
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
            })
            .finally(() => {
            location.reload();
        });
    }
</script>

<script src="../../assets/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>