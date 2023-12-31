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
        tbody tr:hover {
            background-color: #f9f9f9; /* Efecto hover suave */
        }

        .rating {
            display: inline-block;
        }

        .rating input {
            display: none;
        }

        .rating label {
            float: right;
            cursor: pointer;
            color: #ccc;
            transition: color 0.3s;
        }

        .rating label:before {
            content: '\2605';
            font-size: 30px;
        }

        .rating input:checked ~ label,
        .rating label:hover,
        .rating label:hover ~ label {
            color: gold;
            transition: color 0.3s;
        }

        .star-filled, .star-empty {
            font-size: 20px;
            margin-right: 3px;
            color: gold;
        }

        .star-empty {
            color: #ccc;
        }



    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <!-- List existing reviews -->
        <div class="col-12 col-md-6">
            <h6 class="mb-3">Reseñas de nuestros clientes</h6>
            <c:forEach items="${reviews}" var="review">
                <div class="card mb-2">
                    <div class="container m-2">
                        <h6 class="card-title"><c:out value="${review.name_user}" /></h6>
                        <div class="user-rating">
                            <!-- Mostrar estrellas completas según la calificación del usuario -->
                            <c:forEach begin="1" end="${review.evaluacion}" var="star">
                                <span class="star-filled">★</span>
                            </c:forEach>
                            <!-- Mostrar estrellas vacías para el resto hasta 5 -->
                            <c:forEach begin="${review.evaluacion + 1}" end="5" var="emptyStar">
                                <span class="star-empty">☆</span>
                            </c:forEach>
                        </div>
                        <p class="card-text"><c:out value="${review.comentario}" /></p>
                    </div>
                </div>
            </c:forEach>
        </div>
        <!-- Form to submit a new review -->
        <div class="col-12 col-md-6 ">
            <h6 class="mt-5 mb-3">Tu reseña sobre ${item.name}</h6>
            <form action="${pageContext.request.contextPath}/user/review-product" method="post" class="mb-5">
                <input type="hidden" name="productId" value="${productId}" />
                <div class="mb-3">
                    <div class="row">
                        <div class="col-12 col-md-6 col-sm-6">
                            <label class="form-label">¿Qué calificación le darías al producto?</label>
                            <div class="rating">
                                <input value="5" name="rating" id="star5" type="radio">
                                <label for="star5"></label>
                                <input value="4" name="rating" id="star4" type="radio">
                                <label for="star4"></label>
                                <input value="3" name="rating" id="star3" type="radio">
                                <label for="star3"></label>
                                <input value="2" name="rating" id="star2" type="radio">
                                <label for="star2"></label>
                                <input value="1" name="rating" id="star1" type="radio">
                                <label for="star1"></label>
                            </div>
                            <input type="hidden" name="rating" id="rating" value="5" />
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="comment" class="form-label">Comentarios acerca del producto:</label>
                    <textarea id="comment" name="comment" class="form-control" rows="2" required placeholder="E.g., Excelente producto, lo recomendaría..."></textarea>
                </div>
                <input type="hidden" name="itemId" value="${item.id}" />
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