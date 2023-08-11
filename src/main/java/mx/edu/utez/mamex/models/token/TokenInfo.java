package mx.edu.utez.mamex.models.token;

public class TokenInfo {
    private String userId;
    private long expirationTime;

    public TokenInfo(String userId, long expirationTime) {
        this.userId = userId;
        this.expirationTime = expirationTime;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isValid() {
        return System.currentTimeMillis() < expirationTime;
    }
}
