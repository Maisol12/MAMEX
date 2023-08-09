<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="../../layouts/head.jsp"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Reviews</title>
</head>
<body class="bg-light">
<jsp:include page="../../layouts/nav.jsp"/>

<div class="container mt-5">
    <h2 class="mb-4">Product Reviews</h2>

    <!-- List existing reviews -->
    <h3 class="mb-3">Existing Reviews:</h3>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>User ID</th>
            <th>Rating</th>
            <th>Comment</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reviews}" var="review">
            <tr>
                <td><c:out value="${review.userId}" /></td>
                <td><c:out value="${review.rating}" /></td>
                <td><c:out value="${review.comment}" /></td>
                <td><c:out value="${review.date}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Form to submit a new review -->
    <h3 class="mt-4 mb-3">Submit a New Review:</h3>
    <form action="/user/review-product" method="post" class="bg-white p-4 border rounded">
        <input type="hidden" name="productId" value="${productId}" />
        <div class="mb-3">
            <label for="rating" class="form-label">Rating (1-5):</label>
            <input type="number" class="form-control" id="rating" name="rating" min="1" max="5" required />
        </div>
        <div class="mb-3">
            <label for="comment" class="form-label">Comment:</label>
            <textarea class="form-control" id="comment" name="comment" rows="4" required></textarea>
        </div>
        <div class="mb-3">
            <input type="submit" class="btn btn-primary" value="Submit Review" />
        </div>
    </form>
</div>

<jsp:include page="../../layouts/footer.jsp"/>
</body>
</html>
