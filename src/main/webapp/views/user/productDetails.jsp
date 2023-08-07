<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/head.jsp"/>
    <title>Detalles</title>

</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container mt-5 text-black">
    <div class="row">
        <div class="col-lg-6">
            <c:if test="${not empty item.base64Images}">
                <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}" />
                <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="img-fluid rounded mx-auto d-block" alt="${item.name}">
            </c:if>
        </div>
        <div class="col-lg-6">
            <h1>${item.name}</h1>
            <p class="text-muted">${item.description}</p>
            <h2 class="text-primary">$${item.unitPrice}</h2>
            <div class="my-3">
                <p><strong>Stock:</strong> ${item.stock}</p>
                <p><strong>Color:</strong> ${item.color}</p>
                <p><strong>Creado el:</strong> ${item.createDate}</p>
                <p><strong>Última actualización:</strong> ${item.updateDate}</p>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Cantidad</span>
                </div>
                <input type="number" class="form-control" min="1" max="${item.stock}" value="1">
            </div>
            <button class="btn btn-primary btn-lg btn-block">Agregar al carrito</button>
        </div>
    </div>
    <div class="row mt-5">
        <div class="col-12">
            <h2 class="mb-3">Comentarios de los usuarios</h2>
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

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
