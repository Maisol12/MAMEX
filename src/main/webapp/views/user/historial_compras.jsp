<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <h1 class="text-center">Historial de Compras</h1>
    <table class="table table-striped">
      <thead>
      <tr>
        <th>Imagen</th>
        <th>Producto</th>
        <th>Precio Unitario</th>
        <th>Cantidad</th>
        <th>Fecha de Compra</th>
        <th>Total por Artículo</th>
        <th>Reseñar producto</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="sale" items="${userPurchaseHistory}">
        <c:forEach var="purchaseItem" items="${sale.saleItems}">
          <tr>
            <td><img src="data:image/jpeg;base64,${purchaseItem.item.base64Images['image1']}" width="50" height="50" alt="${purchaseItem.item.name}"></td>
            <td>${purchaseItem.item.name}</td>
            <td>$${purchaseItem.item.unitPrice}</td>
            <td>${purchaseItem.quantity}</td>
            <td><fmt:formatDate value="${sale.slDateCreate}" pattern="yyyy-MM-dd" /></td>
            <td>$${purchaseItem.item.unitPrice * purchaseItem.quantity}</td>
            <td>
              <a href="${pageContext.request.contextPath}/user/productDetails?id=${purchaseItem.item.id}" class="btn btn-primary">Reseñar</a>
            </td>
          </tr>
        </c:forEach>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>