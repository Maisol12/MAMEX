<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>Productos</title>
</head>
<body>
<jsp:include page="../../layouts/sidebar.jsp"/>

    <div class="container-fluid custom-container">
        <div class="container pt-5">
            <div class="card text-end">
                <div class="card-title pt-4 pe-3">
                    <a style="text-decoration: none; color:black" href="${pageContext.request.contextPath}/views/admin/crear_producto.jsp">
                        <button class="btn btn-outline-dark btn-sm"><small class="pt-1">Agregar producto</small><i
                                data-feather="plus"></i></button>
                    </a>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped caption-top">
                            <thead>
                            <tr>
                                <th scope="col">Producto</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Descripcion</th>
                                <th scope="col">Color</th>
                                <th scope="col">Inventario</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${items}">
                                <tr>
                                    <td>${item.name}</td>
                                    <td>$${item.unitPrice}</td>
                                    <td>${item.description}</td>
                                    <td>${item.color}</td>
                                    <td>${item.stock}</td>
                                    <td>
                                        <button type="button" class="btn btn-primary btn-sm btn-outline" data-bs-toggle="modal" data-bs-target="#edit-product-modal-${item.id}">
                                            <i data-feather="edit"></i>
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade" id="edit-product-modal-${item.id}" tabindex="-1" aria-labelledby="edit-product-modal-label" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="edit-product-modal-label">Editar Producto</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="${pageContext.request.contextPath}/admin/editar_producto" method="post" enctype="multipart/form-data">
                                                            <input type="hidden" name="id" value="${item.id}">

                                                            <label for="name-${item.id}">Nombre del Producto:</label>
                                                            <input type="text" id="name-${item.id}" name="name" value="${item.name}" required>

                                                            <label for="description-${item.id}">Descripción del Producto:</label>
                                                            <input type="text" id="description-${item.id}" name="description" value="${item.description}" required>

                                                            <label for="color-${item.id}">Color:</label>
                                                            <input type="text" id="color-${item.id}" name="color" value="${item.color}" required>

                                                            <label for="unitPrice-${item.id}">Precio del Producto:</label>
                                                            <input type="text" id="unitPrice-${item.id}" name="unitPrice" value="${item.unitPrice}" required>

                                                            <label for="stock-${item.id}">Cantidad en Stock:</label>
                                                            <input type="number" id="stock-${item.id}" name="stock" value="${item.stock}" required>

                                                            <label for="image1-${item.id}">Imagen del producto 1:</label>
                                                            <input type="file" id="image1-${item.id}" name="image1" accept="image/*">

                                                            <label for="image2-${item.id}">Imagen del producto 2:</label>
                                                            <input type="file" id="image2-${item.id}" name="image2" accept="image/*">

                                                            <label for="image3-${item.id}">Imagen del producto 3:</label>
                                                            <input type="file" id="image3-${item.id}" name="image3" accept="image/*">

                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                                                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button class="btn btn-danger btn-sm btn-outline delete-button" data-id="${item.id}">
                                            <i data-feather="trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>

<jsp:include page="../../layouts/footer.jsp"/>
<script>
    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', event => {
            event.preventDefault();

            const itemId = button.getAttribute('data-id');

            const xhr = new XMLHttpRequest();
            xhr.open('POST', '/admin/delete_product');
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
                if (xhr.status === 200) {
                    window.location.reload();
                } else {
                    // Muestra un mensaje de error...
                }
            };
            xhr.send('id=' + itemId);
        });
    });
</script>

<script>
    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', () => {
            const id = button.getAttribute('data-id');
            const name = button.getAttribute('data-name');
            const description = button.getAttribute('data-description');
            const unitPrice = button.getAttribute('data-unitprice');
            const stock = button.getAttribute('data-stock');

            document.querySelector('#edit-id').value = id;
            document.querySelector('#edit-name').value = name;
            document.querySelector('#edit-description').value = description;
            document.querySelector('#edit-unitPrice').value = unitPrice;
            document.querySelector('#edit-stock').value = stock;
        });
    });
</script>
<script>
    $(document).ready(function() {
        $('#editProductForm').on('submit', function(e) {
            e.preventDefault();

            var formData = new FormData(this);

            $.ajax({
                type: 'POST',
                url: '/admin/editar_producto',
                data: formData,
                contentType: false,
                processData: false,
                success: function(response) {
                    // Aquí puedes manejar la respuesta del servidor.
                    // Por ejemplo, puedes mostrar un mensaje de éxito, actualizar la tabla de productos, etc.
                    alert('Producto actualizado con éxito');
                    location.reload(); // Esta línea recargará la página.
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    // Aquí puedes manejar cualquier error que pueda haber ocurrido durante la solicitud.
                    alert('Error al actualizar el producto');
                }
            });
        });
    });
</script>
<script>
    $(document).ready(function() {
        $('#editForm').submit(function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/products',
                type: 'POST',
                data: formData,
                success: function(data) {
                    // Recarga la página o la tabla de productos aquí
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.error(error);
                },
                cache: false,
                contentType: false,
                processData: false
            });
        });
    });
</script>
<script>
    function previewImage(input, previewId) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById(previewId).style.backgroundImage = 'url(' + e.target.result + ')';
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    document.getElementById('file-input1').addEventListener('change', function() {
        previewImage(this, 'image-preview1');
    });

    document.getElementById('file-input2').addEventListener('change', function() {
        previewImage(this, 'image-preview2');
    });

    document.getElementById('file-input3').addEventListener('change', function() {
        previewImage(this, 'image-preview3');
    });
</script>

<jsp:include page="../../layouts/footer.jsp"/>

</body>
</html>
