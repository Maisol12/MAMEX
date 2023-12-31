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
  <title>Categorías</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container-fluid text-start align-items-start border">
  <!-- Aquí empieza el código para mostrar los productos agrupados por categoría -->
  <c:forEach var="categoryEntry" items="${productsByCategory}">
    <div class="row">
      <div class="col-12">
        <h5 class="mt-4 mb-4">${categoryEntry.key}</h5> <!-- Nombre de la categoría -->
      </div>
    </div>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
      <c:forEach var="item" items="${categoryEntry.value}">
        <div class="col d-flex align-items-start mb-3">
          <div class="card h-100" style="width: 260px">
            <c:if test="${not empty item.base64Images}">
              <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}"/>
              <a class="text-decoration-none" href="${pageContext.request.contextPath}/user/productDetails?id=${item.id}">
                <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top"
                     alt="${item.name}" style="max-height: 200px;">
              </a>
            </c:if>
            <div class="card-body">
              <h6 class="card-title">${item.name}</h6>
              <small class="text-muted mb-2">${item.category}</small>
              <h6>$${item.unitPrice}</h6>
              <small class="card-text fw-lighter" style="font-size: 14px;">${item.description}</small>
            </div>
            <div class="card-footer bg-white border-top-0">
              <div class="row row-cols-2 justify-content-around">
                <button class="btn btn-sm btn-outline-dark w-100" onclick="addToCart(${item.id})" style="border-radius: 0;">
                  <small class="text">Agregar <i data-feather="shopping-bag" style="width: 14px; height: 14px"></i></small>
                </button>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </c:forEach>
  <!-- Aquí termina el código para mostrar los productos -->

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