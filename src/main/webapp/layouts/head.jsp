<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Merriweather:wght@300&display=swap" rel="stylesheet">
<link rel="icon" href="${pageContext.request.contextPath}/assets/img/OIP.jpg">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nav.css">
<script src="${pageContext.request.contextPath}/assets/js/alertify.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/alertify.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/default.min.css">

<style>
    .icon {
        color: white;
    }
    .icon:hover{
        color: #EAB543;
    }
    .icon-button {
        background: none;
        border: none;
        color: inherit;
        cursor: pointer;
    }
    .micarrusel{
        height: 500px !important;
    }

    .carousel-item{
        width: 100% !important;
        height: 500px !important;
    }
    /* Media query para pantallas pequeñas */
    @media screen and (max-width: 767px) {
        .micarrusel {
            height: auto !important;
        }

        .carousel-item {
            height: auto !important;
        }
    }
    @media screen and (max-width: 767px) {
        .custom-container {
            width: auto;
            height: auto !important;
        }

        .profile-card {
            width: auto;
            height: auto !important;
        }
    }
    .links{
        text-decoration: none;
        color: black;
    }
    .profile-img{
        width: 20%;
        height: 20%;
        border-radius: 50%;
        margin-top: 24px;
    }

    .cards-ventas{
        flex: 1 0 auto;
        margin: 0 30px;
        background-color: white;
        width: 224px;
        height: 400px;
    }

    .cards-ventas img{
        width: 100%;
        height: 300px;
    }

    .custom-card {
        max-width: 260px !important;
        border-radius: 0;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15), 0 3px 6px rgba(0, 0, 0, 0.1);
        transition: box-shadow 0.3s ease;
    }

    .custom-card:hover {
        box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 5px 15px rgba(0, 0, 0, 0.15);
    }

    .card-img-top {
        height:220px;
        object-fit: cover;
        border-radius: 0 !important;

    }

    .card-footer {
        background-color: transparent !important;

    }



</style>