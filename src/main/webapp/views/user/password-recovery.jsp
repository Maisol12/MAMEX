<%--
  Created by IntelliJ IDEA.
  User: aldair
  Date: 11/08/23
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cambio de Contraseña</title>
  <jsp:include page="../../layouts/head.jsp"/></head>
<body class="bg-light">
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container my-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card shadow">
        <div class="card-header bg-primary text-white text-center">
          <h4>Cambio de Contraseña</h4>
        </div>
        <div class="card-body">
            <div class="mb-3">
              <label for="newPassword" class="form-label">Nueva Contraseña</label>
              <input type="password" class="form-control" id="newPassword" name="newPassword" required>
            </div>
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
              <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
            </div>
            <input type="hidden" name="token" value="${param.token}">
            <div class="d-grid">
              <form novalidate action="${pageContext.request.contextPath}/user/password-recovery" method="post">
                <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
              </form>

            </div>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>

