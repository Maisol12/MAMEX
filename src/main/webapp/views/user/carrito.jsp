<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<div class="container position-absolute top-50 start-50 translate-middle w-75">
  <div class="container">
    <h1 class="text-center">Tu Carrito</h1>
    <form action="${pageContext.request.contextPath}/user/checkout" method="post">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Imagen</th>
          <th>Producto</th>
          <th>Precio Unitario</th>
          <th>Cantidad</th>
          <th>Total por Artículo</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cartItem" items="${sessionScope.cart.items}">
          <tr>
            <td><img src="data:image/jpeg;base64,${cartItem.item.base64Images['image1']}" width="50" height="50" alt="${cartItem.item.name}"></td>
            <td>${cartItem.item.name}</td>
            <td>$${cartItem.item.unitPrice}</td>
            <td>
              <input type="number" name="quantity_${cartItem.item.id}" value="${cartItem.quantity}" min="1" style="width: 50px;">
            </td>
            <td>$${cartItem.item.unitPrice * cartItem.quantity}</td>
            <td>
              <a href="${pageContext.request.contextPath}/user/remove-from-cart?itemId=${cartItem.item.id}" class="btn btn-danger">Eliminar</a>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="d-flex justify-content-between align-items-center">
        <div>
          <strong>Total: $${sessionScope.cart.getTotalPrice()}</strong>
        </div>
        <input type="submit" value="Aceptar" class="btn btn-success"/>
      </div>
    </form>
  </div>
</div>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
