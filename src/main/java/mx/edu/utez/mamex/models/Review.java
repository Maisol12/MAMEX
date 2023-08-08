package mx.edu.utez.mamex.models;

public class Review {
    private long id;
    private long userId;
    private long productId;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(long id, long productId, int rating, String comment) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
