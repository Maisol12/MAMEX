<%--
  Created by IntelliJ IDEA.
  User: albertovazquez
  Date: 09/07/23
  Time: 7:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="icon" href="${pageContext.request.contextPath}/assets/img/MAMEX.jpg">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/alertify.min.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/default.min.css">

<style>
    .custom-container {
        background-color: #EEF0F2;
        min-height: 100vh;
    }

    .offcanvas-backdrop.show {
        opacity: 0 !important;
    }

    .nav-link{
        color: #000000;
    }
    .nav-link:hover{
        color: mediumpurple;
    }

    /* From uiverse.io by @satyamchaudharydev */
    /* removing default style of button */

    .form button {
        border: none;
        background: none;
        color: #8b8ba7;
    }
    /* styling of whole input container */
    .form {
        --timing: 0.3s;
        --width-of-input: 200px;
        --height-of-input: 40px;
        --border-height: 2px;
        --input-bg: #fff;
        --border-color: #2f2ee9;
        --border-radius: 30px;
        --after-border-radius: 1px;
        position: relative;
        width: var(--width-of-input);
        height: var(--height-of-input);
        display: flex;
        align-items: center;
        padding-inline: 0.8em;
        border-radius: var(--border-radius);
        transition: border-radius 0.5s ease;
        background: var(--input-bg,#fff);
    }
    /* styling of Input */
    .input {
        font-size: 0.9rem;
        background-color: transparent;
        width: 100%;
        height: 100%;
        padding-inline: 0.5em;
        padding-block: 0.7em;
        border: none;
    }
    /* styling of animated border */
    .form:before {
        content: "";
        position: absolute;
        background: var(--border-color);
        transform: scaleX(0);
        transform-origin: center;
        width: 100%;
        height: var(--border-height);
        left: 0;
        bottom: 0;
        border-radius: 1px;
        transition: transform var(--timing) ease;
    }
    /* Hover on Input */
    .form:focus-within {
        border-radius: var(--after-border-radius);
    }
    /* Eliminar el borde negro al hacer hover sobre el input */

    input:focus {
        outline: none !important;
    }
    /* here is code of animated border */
    .form:focus-within:before {
        transform: scale(1);
    }
    /* styling of close button */
    /* == you can click the close button to remove text == */
    .reset {
        border: none;
        background: none;
        opacity: 0;
        visibility: hidden;
    }
    /* close button shown when typing */
    input:not(:placeholder-shown) ~ .reset {
        opacity: 1;
        visibility: visible;
    }
    /* sizing svg icons */
    .form svg {
        width: 17px;
        margin-top: 3px;
    }
    .custom-input:hover{
        outline: none !important;
        border: none !important;
    }
</style>
