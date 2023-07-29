<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/headAdmin.jsp"/>
    <title>MAMEX</title>
</head>
<body>
<jsp:include page="../../layouts/sidebar.jsp"/>
<main>
    <div class="container-main">
        <div class="card mt-4">
            <h6 class="text-start mt-4 ms-3 fw-bold">Nuevo Producto</h6>

            <div class="card-body">
                <div class="container">
                    <div class="row d-flex ">
                        <div class="col-sm-12 col-lg-6 col-md-7">
                            <h4></h4>
                            <div class="container">
                                <small>Para agregar un nuevo producto, por favor completa todos los campos requeridos a continuación.</small>
                                <form action="${pageContext.request.contextPath}/admin/crear_producto" method="post" class="mt-3" enctype="multipart/form-data">
                                    <small class="fw-bold">Nombre del Producto*</small>
                                    <input type="text" class="form-control w-50 mb-2" name="name" placeholder="Nombre del Producto" aria-label="Nombre del Producto" required>
                                    <br>
                                    <small class="fw-bold">Descripción del Producto*</small>
                                    <input type="text" class="form-control w-75 mb-2" name="description" placeholder="Descripción del Producto" aria-label="Descripción del Producto" required>
                                    <br>
                                    <small class="fw-bold">Color*</small>
                                    <input type="text" class="form-control w-75 mb-2" name="color" placeholder="Color" aria-label="Color del Producto" required>
                                    <br>
                                    <small class="fw-bold">Precio del Producto*</small>
                                    <input type="text" class="form-control w-50 mb-2" name="unitPrice" placeholder="Precio del Producto" aria-label="Precio del Producto" required>
                                    <br>
                                    <small class="fw-bold">Cantidad en Stock*</small>
                                    <input type="number" class="form-control w-50 mb-2" name="stock" placeholder="Cantidad en Stock" aria-label="Cantidad en Stock" required>
                                    <br>
                                    <small class="fw-bold">Notas*</small>
                                    <input type="text" class="form-control w-75 mb-2" name="notes" placeholder="Notas adicionales" aria-label="Notas adicionales" required>
                                    <br>
                                    <small class="fw-bold">Fecha de Creación*</small>
                                    <input type="date" class="form-control w-50 mb-2" name="createDate" aria-label="Fecha de Creación" required>
                                    <br>
                                    <small class="fw-bold">Fecha de Actualización*</small>
                                    <input type="date" class="form-control w-50 mb-2" name="updateDate" aria-label="Fecha de Actualización" required>
                                    <br>
                                    <div>
                                        <small class="fw-bold">Imágenes del producto*</small>
                                        <div>
                                            <small class="fw-bold">Imagen del producto 1*</small>
                                            <input type="file" id="file-input1" name="image1" accept="image/*">
                                            <div id="image-preview1" class="image-preview"></div>
                                        </div>
                                        <div>
                                            <small class="fw-bold">Imagen del producto 2*</small>
                                            <input type="file" id="file-input2" name="image2" accept="image/*">
                                            <div id="image-preview2" class="image-preview"></div>
                                        </div>
                                        <div>
                                            <small class="fw-bold">Imagen del producto 3*</small>
                                            <input type="file" id="file-input3" name="image3" accept="image/*">
                                            <div id="image-preview3" class="image-preview"></div>
                                        </div>
                                    </div>
                                    <input type="submit" class="btn btn-primary" value="Agregar Producto">
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../../layouts/footer.jsp"/>
<script>
    document.getElementById('file-input1').addEventListener('change', function(e) {
        previewImage(e, 'image-preview1');
    });
    document.getElementById('file-input2').addEventListener('change', function(e) {
        previewImage(e, 'image-preview2');
    });
    document.getElementById('file-input3').addEventListener('change', function(e) {
        previewImage(e, 'image-preview3');
    });
    function previewImage(e, previewAreaId) {
        let previewArea = document.getElementById(previewAreaId);

        // Clear the preview area before adding new image
        while(previewArea.firstChild) {
            previewArea.firstChild.remove();
        }

        let file = e.target.files[0];

        if(!file.type.startsWith('image/')){ return }

        let img = document.createElement('img');
        img.classList.add('obj');
        img.file = file;

        let container = document.createElement('div');
        container.appendChild(img);
        previewArea.appendChild(container);

        // Read the file
        let reader = new FileReader();
        reader.onload = (function(aImg) {
            return function(e) {
                aImg.src = e.target.result;
            };
        })(img);
        reader.readAsDataURL(file);
    }
</script>

</body>
</html>



