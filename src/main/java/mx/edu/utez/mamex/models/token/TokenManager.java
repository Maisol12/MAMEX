package mx.edu.utez.mamex.models.token;
import java.util.List;
import java.util.ArrayList;


public class TokenManager {

    // Supongamos que tienes una lista o base de datos de tokens.
    // Por simplicidad, vamos a usar una lista. En un caso real, probablemente querrías usar una base de datos.
    private static List<TokenInfo> tokens = new ArrayList<>();

    public static TokenInfo getTokenInfo(String token) {
        // Buscar el token en la lista o base de datos.
        for (TokenInfo tokenInfo : tokens) {
            if (tokenInfo.getToken().equals(token)) {
                return tokenInfo;
            }
        }
        return null;
    }

    public static boolean removeToken(String token) {
        // Elimina el token de la lista o marca como utilizado en la base de datos.
        return tokens.removeIf(tokenInfo -> tokenInfo.getToken().equals(token));
    }

    public static void addToken(TokenInfo tokenInfo) {
        tokens.add(tokenInfo);
    }
}
