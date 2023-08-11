package mx.edu.utez.mamex.models.token;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {
    private static Map<String, TokenInfo> tokenMap = new HashMap<>();

    public static void addToken(String token, String userId, long expirationTime) {
        tokenMap.put(token, new TokenInfo(userId, expirationTime));
    }

    public static TokenInfo getTokenInfo(String token) {
        return tokenMap.get(token);
    }

    public static void removeToken(String token) {
        tokenMap.remove(token);
    }
}
