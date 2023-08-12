package mx.edu.utez.mamex.models.token;

import java.util.Date;

public class TokenInfo {
    private String token;
    private Date expiryDate;
    private boolean used;
    private String userId;  // Añade el atributo userId


    public TokenInfo(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.used = false;
    }

    public String getUserId() {  // Método getter para userId
        return userId;
    }

    public String getToken() {
        return token;
    }

    public boolean isValid() {
        Date now = new Date();
        return !used && expiryDate.after(now);
    }

    public void markAsUsed() {
        this.used = true;

    }
}
