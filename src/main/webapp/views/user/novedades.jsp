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
    <title>Novedades</title>
</head>
<body>
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container text-center">
    <div class="row">
        <div class="col-12">
            <h3 class="mt-4 mb-4">Lo más nuevo en <em>Manos Mexicanas</em></h3>
        </div>
    </div>
    <div class="row mb-4">
        <div class="col-md-9"></div>
        <div class="col-md-3">
            <h5>Filtrar por:</h5>
            <form action="${pageContext.request.contextPath}/admin/filterProducts" method="GET">
                <div class="mb-3">
                    <label for="categoryFilter" class="form-label">Categoría</label>
                    <select class="form-select" id="categoryFilter" name="category">
                        <option selected value="">Elige una categoría...</option>
                        <optgroup label="Ropa Tejida">
                            <option value="Suéteres">Suéteres</option>
                            <option value="Bufandas">Bufandas</option>
                            <option value="Gorros">Gorros</option>
                            <option value="Guantes">Guantes</option>
                            <option value="Vestidos">Vestidos</option>
                            <option value="Pantalones">Pantalones</option>
                        </optgroup>
                        <optgroup label="Bolsas y Accesorios">
                            <option value="Bolsos de mano">Bolsos de mano</option>
                            <option value="Mochilas">Mochilas</option>
                            <option value="Carteras">Carteras</option>
                            <option value="Estuches">Estuches</option>
                            <option value="Monederos">Monederos</option>
                        </optgroup>
                        <optgroup label="Juguetes y Muñecos">
                            <option value="Animales">Animales</option>
                            <option value="Personajes">Personajes</option>
                            <option value="Muñecas">Muñecas</option>
                        </optgroup>
                        <optgroup label="Decoración para el Hogar">
                            <option value="Cojines">Cojines</option>
                            <option value="Mantas">Mantas</option>
                            <option value="Tapetes">Tapetes</option>
                            <option value="Cortinas">Cortinas</option>
                        </optgroup>
                        <optgroup label="Bebés y Niños">
                            <option value="Ropita para bebés">Ropita para bebés</option>
                            <option value="Mantitas">Mantitas</option>
                            <option value="Juguetes tejidos">Juguetes tejidos</option>
                            <option value="Zapatos">Zapatos</option>
                            <option value="Accesorios de Moda">Accesorios de Moda</option>
                        </optgroup>
                        <optgroup label="Joyería tejida">
                            <option value="Diademas y cintillos">Diademas y cintillos</option>
                            <option value="Broches">Broches</option>
                            <option value="Lazos y cintas">Lazos y cintas</option>
                        </optgroup>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="priceFilter" class="form-label">Rango de precios</label>
                    <select class="form-select" id="priceFilter" name="priceRange">
                        <option selected value="">Elige un rango...</option>
                        <option value="0-100">$0 - $100</option>
                        <option value="100-500">$100 - $500</option>
                        <!-- Agregar otros rangos aquí según sean necesarios -->
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Filtrar</button>
            </form>
        </div>
    </div>
    <div class="row row-cols-1 row-cols-sm-1 row-cols-md-3 row-cols-lg-4 g-4 justify-content-center text-lg-start text-sm-center">
        <c:forEach var="item" items="${items}">
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center">
                <div class="card custom-card h-100 position-relative">
                    <c:if test="${not empty item.base64Images}">
                        <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}"/>
                        <a class="text-decoration-none" href="${pageContext.request.contextPath}/user/productDetails?id=${item.id}">
                            <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top"
                                 alt="${item.name}">
                        </a>
                    </c:if>
                    <div class="card-body">
                        <h6 class="card-title">${item.name}</h6>
                        <p class="text-muted">${item.category}</p>
                        <h6>$${item.unitPrice}</h6>
                        <small class="card-text fw-lighter" style="font-size: 14px;">${item.description}</small>
                    </div>
                    <div class="card-footer bg-light">
                        <div class="row row-cols-2 justify-content-around">
                            <button class="btn btn-sm btn-outline-dark w-100" onclick="addToCart(${item.id})" style="border-radius: 0;">
                                <small class="text">Agregar <i data-feather="shopping-cart" style="width: 14px; height: 14px"></i></small>
                            </button>
                        </div>
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
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }
</script>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
