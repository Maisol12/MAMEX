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
<main>
    <div class="container-main">
        <div class="card text-end">
            <div class="card-title pt-4 pe-3">
                <a style="text-decoration: none; color:black" href="${pageContext.request.contextPath}/views/admin/crear_producto.jsp">
                    <small>Agregar productos</small><button class="btn btn-success btn-sm btn-outline pt-2 ms-2"><i data-feather="plus"></i></button>
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped caption-top">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Producto</th>
                            <th scope="col">Precio</th>
                            <th scope="col">Descripcion</th>
                            <th scope="col">Disponibilidad</th>
                            <th scope="col">Inventario</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <th scope="row">${item.id}</th>
                                <td>${item.name}</td>
                                <td>$${item.unitPrice}</td>
                                <td>${item.description}</td>
                                <td>${item.available}</td>
                                <td>${item.stock}</td>
                                <td>
                                    <button class="btn btn-primary btn-sm btn-outline edit-button"
                                            data-bs-toggle="modal" data-bs-target="#editProductModal"
                                            data-id="${item.id}" data-name="${item.name}"
                                            data-description="${item.description}" data-unitprice="${item.unitPrice}"
                                            data-available="${item.available}" data-stock="${item.stock}">
                                        <i data-feather="edit"></i>
                                    </button>
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

        <!-- Modal para editar un producto -->
        <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editProductModalLabel">Editar Producto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Aquí va el formulario de edición que has proporcionado -->
                        <form action="${pageContext.request.contextPath}/admin/editar_producto" method="post" class="mt-3" enctype="multipart/form-data">
                            <input type="hidden" id="edit-id" name="id">
                            <small class="fw-bold">Nombre del Producto*</small>
                            <input type="text" class="form-control w-50 mb-2" id="edit-name" name="name" placeholder="Nombre del Producto" aria-label="Nombre del Producto" required>
                            <br>
                            <small class="fw-bold">Descripción del Producto*</small>
                            <input type="text" class="form-control w-75 mb-2" id="edit-description" name="description" placeholder="Descripción del Producto" aria-label="Descripción del Producto" required>
                            <br>
                            <small class="fw-bold">Precio del Producto*</small>
                            <input type="text" class="form-control w-50 mb-2" id="edit-unitPrice" name="unitPrice" placeholder="Precio del Producto" aria-label="Precio del Producto" required>
                            <br>
                            <small class="fw-bold">Cantidad en Stock*</small>
                            <input type="number" class="form-control w-50 mb-2" id="edit-stock" name="stock" placeholder="Cantidad en Stock" aria-label="Cantidad en Stock" required>
                            <br>
                            <input type="submit" class="btn btn-primary" value="Guardar Cambios">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

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
        $('#editForm').submit(function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/editar_producto',
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
</body>
</html>
