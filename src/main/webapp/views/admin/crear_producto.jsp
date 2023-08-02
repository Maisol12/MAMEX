<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>MAMEX</title>
</head>
<body>
<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-fluid custom-container">
        <div class="container pt-4">
            <div class="card p-3" style="border-radius: 0px;">
                <h5 class="mb-4">Agregar producto</h5>
                <form action="${pageContext.request.contextPath}/admin/crear_producto" method="post"
                      enctype="multipart/form-data">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Nombre del producto*</label>
                            <input type="text" class="form-control" name="name" placeholder="Nombre del Producto"
                                   required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Descripción del producto*</label>
                            <input type="text" class="form-control" name="description"
                                   placeholder="Descripción del Producto" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Color*</label>
                            <input type="text" class="form-control" name="color" placeholder="Color del Producto"
                                   required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Precio del Producto*</label>
                            <input type="text" class="form-control" name="unitPrice" placeholder="Precio del Producto"
                                   required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Cantidad en Stock*</label>
                            <input type="number" class="form-control" name="stock" placeholder="Cantidad en Stock"
                                   required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Notas*</label>
                            <input type="text" class="form-control" name="notes" placeholder="Notas adicionales"
                                   required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Fecha de Creación*</label>
                            <input type="date" class="form-control" name="createDate" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Fecha de Actualización*</label>
                            <input type="date" class="form-control" name="updateDate" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-12">
                            <label class="form-label">Imágenes del producto*</label>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Imagen del producto</label>
                            <input type="file" class="form-control" name="image1" accept="image/*">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Imagen del producto</label>
                            <input type="file" class="form-control" name="image2" accept="image/*">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Imagen del producto</label>
                            <input type="file" class="form-control" name="image3" accept="image/*">
                        </div>
                    </div>
                    <div class="container text-end mb-0">
                        <a href="javascript:void(0);" onclick="redirectToProductsPage();" class="btn btn-small btn-outline-danger">Cancelar</a>
                        <input type="submit" class="btn btn-small btn-outline-dark" value="Agregar">
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>
<script>
    document.getElementById('file-input1').addEventListener('change', function (e) {
        previewImage(e, 'image-preview1');
    });
    document.getElementById('file-input2').addEventListener('change', function (e) {
        previewImage(e, 'image-preview2');
    });
    document.getElementById('file-input3').addEventListener('change', function (e) {
        previewImage(e, 'image-preview3');
    });

    function previewImage(e, previewAreaId) {
        let previewArea = document.getElementById(previewAreaId);

        // Clear the preview area before adding new image
        while (previewArea.firstChild) {
            previewArea.firstChild.remove();
        }

        let file = e.target.files[0];

        if (!file.type.startsWith('image/')) {
            return
        }

        let img = document.createElement('img');
        img.classList.add('obj');
        img.file = file;

        let container = document.createElement('div');
        container.appendChild(img);
        previewArea.appendChild(container);

        // Read the file
        let reader = new FileReader();
        reader.onload = (function (aImg) {
            return function (e) {
                aImg.src = e.target.result;
            };
        })(img);
        reader.readAsDataURL(file);
    }

    function redirectToProductsPage() {
        window.location.href = "${pageContext.request.contextPath}/admin/products";
    }

</script>

</body>
</html>



