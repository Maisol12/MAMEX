package mx.edu.utez.mamex.utils;

import java.util.Random;

public class PasswordGenerator {

    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}

