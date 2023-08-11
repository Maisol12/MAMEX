<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../../layouts/head.jsp"/>
    <title>Reviews</title>
    <style>
        table {
            border-spacing: 0 15px;
            width: 100%;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #f2f2f2; /* Borde claro */
        }

        .rating .star.selected i {
            border: 2px solid #ffcc00; /* Borde de color amarillo */
            border-radius: 50%; /* Hace que el borde sea redondeado */
            padding: 2px; /* Espacio entre el icono de la estrella y el borde */
        }


        tbody tr:hover {
            background-color: #f9f9f9; /* Efecto hover suave */
        }
        .rating .star {
            cursor: pointer;
            display: inline-block;
            margin-right: 5px;
            font-size: 24px;
            transition: color 0.2s;
        }

        .rating .star:last-child {
            margin-right: 0;
        }

        .rating .star:hover,
        .rating .star[data-active='true'] {
            color: #ffcc00; /* Cambia el color según lo que desees */
        }


    </style>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <!-- List existing reviews -->
        <div class="col-12 col-md-6">
            <h3 class="mb-3">Reseñas de nuestros clientes:</h3>
            <table class="table table-responsive table-striped">
                <thead>
                <tr>
                    <th>Usuario</th>
                    <th>Calificación</th>
                    <th>Comentarios</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${reviews}" var="review">
                    <tr>
                        <td><c:out value="${review.name_user}" /></td>
                        <td><c:out value="${review.evaluacion}" /></td>
                        <td><c:out value="${review.comentario}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Form to submit a new review -->
        <div class="col-12 col-md-6 ">
            <h3 class="mt-5 mb-3">Tu reseña sobre ${item.name}</h3>
            <form action="${pageContext.request.contextPath}/user/review-product" method="post" class="mb-5">
                <input type="hidden" name="productId" value="${productId}" />
                <div class="mb-3">
                    <label class="form-label">¿Qué calificación le darías al producto?</label>
                    <div class="rating">
                        <span class="star" data-value="1"><i data-feather="star"></i></span>
                        <span class="star" data-value="2"><i data-feather="star"></i></span>
                        <span class="star" data-value="3"><i data-feather="star"></i></span>
                        <span class="star" data-value="4"><i data-feather="star"></i></span>
                        <span class="star" data-value="5"><i data-feather="star"></i></span>
                        <input type="hidden" id="rating" name="rating" value="">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="comment" class="form-label">Comentarios acerca del producto:</label>
                    <textarea id="comment" name="comment" class="form-control" rows="2" required placeholder="E.g., Excelente producto, lo recomendaría..."></textarea>
                </div>

                <div>
                    <input type="submit" value="Publicar" class="btn btn-submit btn-sm btn-outline-dark" />
                </div>
            </form>

        </div>
    </div>
</div>
<script>
    feather.replace();
    $(document).ready(function() {
        $('.rating .star').click(function() {
            console.log("Star clicked!");

            let value = $(this).data('value');
            $('#rating').val(value);

            // Remove "selected" class from all stars
            $('.rating .star').removeClass('selected');
            $('.rating .star i').attr('data-feather', 'star'); // Reset all stars to unfilled

            // Mark only the clicked star as selected
            $(this).addClass('selected');
            $(this).find('i').attr('data-feather', 'star-fill');
            console.log("Star with value " + value + " is selected!");

            feather.replace(); // re-render the icons
        });
    });
</script>


<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>