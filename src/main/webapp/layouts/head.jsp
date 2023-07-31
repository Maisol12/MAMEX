<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;600&family=Raleway:wght@300;400;500&display=swap" rel="stylesheet">
<link rel="icon" href="${pageContext.request.contextPath}/assets/img/OIP.jpg">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nav.css">
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
</style>


