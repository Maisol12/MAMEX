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
  <title>Manos mexicanas</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container">
  <div class="row">
    <div class="col-12">

    </div>
  </div>
  <div class="row">
    <c:forEach var="item" items="${items}">
      <div class="col-lg-4 col-md-6 mb-4">
        <div class="card h-100">
          <c:if test="${not empty item.base64Images}">
            <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}" />
            <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top" alt="${item.name}">
          </c:if>
          <div class="card-body">
            <h5 class="card-title">${item.name}</h5>
            <h6>${item.unitPrice} $</h6>
            <p class="card-text">${item.description}</p>
            <button type="button" class="btn btn-primary" onclick="addToCart(${item.id})">Agrega al carrito</button>
          </div>
          <div class="card-footer">
            <a href="/user/productDetails?id=${item.id}" class="btn btn-primary">Ver más</a>
          </div>
        </div>
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
