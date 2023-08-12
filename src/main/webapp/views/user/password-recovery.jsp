@@ -0,0 +1,51 @@
<%--
  Created by IntelliJ IDEA.
  User: aldair
  Date: 11/08/23
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    String encodedEmail = request.getParameter("email");
    byte[] decodedBytes = java.util.Base64.getDecoder().decode(encodedEmail);
    String decodedEmail = new String(decodedBytes);
%>

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
                    <form id="passwordRecoveryForm" action="${pageContext.request.contextPath}/user/password-recovery" method="post">
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Nueva Contraseña</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                        </div>
                        <input type="hidden" name="token" value="${param.token}">
                        <input type="hidden" name="decodedEmail" value="<%= decodedEmail %>">
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('passwordRecoveryForm').addEventListener('submit', function(event) {
        console.log("Formulario enviado");
        console.log("newPassword: " + document.getElementById('newPassword').value);
        console.log("confirmPassword: " + document.getElementById('confirmPassword').value);
        console.log("token: " + getUrlParameter('token'));
    });

    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }
</script>
<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>
