<%--
  Created by IntelliJ IDEA.
  User: josue
  Date: 29/05/2023
  Time: 02:14 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/feather.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/alertify.min.js"></script>


<script>
    feather.replace()
</script>

<script>
    document.getElementById("unlogin").addEventListener("click", function (){
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/user/unlogin',  true);
        xhr.setRequestHeader('Content-Type', 'aplication/x-wwww-form-urlencoded');
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4 && xhr.status == 200){
                alert('Se ha cerrado la sesion correctamente')
            }
        };
        xhr.send('logout=true');
    });
</script>