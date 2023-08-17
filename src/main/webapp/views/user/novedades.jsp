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

<div class="container-fluid">
    <h4 class="text-center mt-4">Lo más nuevo en <em>Manos Mexicanas</em></h4>
    <div class="row mt-5 ms-2">
        <!-- Filtros en la columna izquierda -->
        <div class="col-md-2">
            <h6 class="text-center">Filtrar por:</h6>
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
                        <option value="500-1000">$500 - $1000</option>
                        <option value="1000-2000">$1000 - $2000</option>
                        <option value="2000-5000">$2000 - $5000</option>
                        <!-- Agregar otros rangos aquí según sean necesarios -->
                    </select>
                </div>
                <div class="text-center">
                    <div class="row">
                        <form novalidate action="${pageContext.request.contextPath}/user/novedades" method="get">
                            <button type="submit" class="btn btn-outline-dark mb-3 btn-sm" style="border-radius: 0">Filtrar <i data-feather="filter" style="width: 14px; height: 14px"></i></button>
                        </form>
                        <a href="${pageContext.request.contextPath}/user/novedades" class="btn btn-outline-danger btn-sm" style="border-radius: 0">Eliminar filtros <i data-feather="x" style="width: 16px; height: 16px; margin-bottom: 3px"></i></a>

                    </div>
                </div>
            </form>
        </div>

        <!-- Cards de productos en la columna derecha -->
        <div class="col-md-10">
            <div class="row g-3">
                <c:forEach var="item" items="${items}">
                    <div class="col-sm-6 col-md-4 col-lg-3 mb-4">
                        <div class="card position-relative h-100 mt-4 mb-3">
                            <!-- Imagen del producto -->
                            <c:if test="${not empty item.base64Images}">
                                <c:set var="imageName" value="${item.base64Images.keySet().iterator().next()}"/>
                                <a class="text-decoration-none" href="${pageContext.request.contextPath}/user/productDetails?id=${item.id}">
                                    <img src="data:image/jpeg;base64,${item.base64Images[imageName]}" class="card-img-top"
                                         alt="${item.name}">
                                </a>
                            </c:if>
                            <div class="card-body">
                                <h6 class="card-title">${item.name}</h6>
                                <p class="text-muted d-lg-block d-none">${item.category}</p>
                                <p class="fw-lighter ">$${item.unitPrice}</p>
                                <small class="card-text fw-light d-lg-block d-none" style="font-size: 14px;">${item.description}</small>
                            </div>
                            <div class="card-footer bg-white">
                                <div class="row">
                                    <button class="btn btn-sm btn-outline-dark w-100" onclick="addToCart(${item.id})" style="border-radius: 0;">
                                        <small class="text">Agregar <i data-feather="shopping-bag" style="width: 14px; height: 14px"></i></small>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
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
                return response.json();
            })
            .then(data => {
                // Actualiza el contador de elementos en el carrito
                document.getElementById('cartItemCount').textContent = data.cart.totalQuantity;
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            })
            .finally(() => {
                location.reload();
            });
    }
</script>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>