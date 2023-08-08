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
        <div class="col-md-6">
            <c:if test="${not empty item.base64Images}">
                <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}" />
                <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="img-fluid rounded" alt="${item.name}">
            </c:if>
        </div>
        <div class="col-md-6">
            <h1>${item.name}</h1>
            <h3 class="text-success">$${item.unitPrice}</h3>
            <p>${item.description}</p>
            <h4>Detalles</h4>
            <p><strong>Stock disponible:</strong> ${item.stock}</p>
            <p><strong>Color:</strong> ${item.color}</p>
            <button class="btn btn-primary btn-lg btn-block mt-3">Agregar al carrito</button>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-12">
            <h2>Comentarios de los usuarios</h2>
            <c:forEach var="comment" items="${comments}">
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${comment.user}</h5>
                        <p class="card-text">${comment.text}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
