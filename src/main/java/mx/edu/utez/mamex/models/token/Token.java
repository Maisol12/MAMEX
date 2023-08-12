package mx.edu.utez.mamex.models.token;

import java.util.UUID;

public class Token {
    public static String tokenGenerator() {
        UUID token = UUID.randomUUID();
        return token.toString(); // No reemplazar los guiones en blanco
    }
}
