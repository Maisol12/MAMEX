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

<div class="container position-absolute top-50 start-50 translate-middle w-50">
  <div class="container">
    <h1 class="text-center">Tu Carrito</h1>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>Producto</th>
        <th>Cantidad</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="cartItem" items="${sessionScope.cart.items}">
        <tr>
          <td>${cartItem.item.name}</td>
          <td>${cartItem.quantity}</td>
          <td>
            <a href="${pageContext.request.contextPath}/user/remove-from-cart?itemId=${cartItem.item.id}" class="btn btn-danger">Eliminar</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div class="d-flex justify-content-end">
      <form action="${pageContext.request.contextPath}/user/checkout" method="post">
        <input type="submit" value="Checkout" class="btn btn-success"/>
      </form>
    </div>
  </div>
</div>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>

