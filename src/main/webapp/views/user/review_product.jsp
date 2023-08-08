<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reviews</title>
</head>
<body>

<h2>Product Reviews</h2>

<!-- List existing reviews -->
<h3>Existing Reviews:</h3>
<table border="1">
    <thead>
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
<h3>Submit a New Review:</h3>
<form action="/user/review-product" method="post">
    <input type="hidden" name="productId" value="${productId}" />
    <div>
        <label for="rating">Rating (1-5):</label>
        <input type="number" id="rating" name="rating" min="1" max="5" required />
    </div>
    <div>
        <label for="comment">Comment:</label>
        <textarea id="comment" name="comment" rows="4" required></textarea>
    </div>

    <div>
        <input type="submit" value="Submit Review" />
    </div>
</form>

</body>
</html>
