package mx.edu.utez.mamex.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public void sendEmail(String toEmail, String subject, String message) {
        // Set mail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Set your email and password
        String mailFrom = "tiendamanosmexicanas1@gmail.com";
        String password = "eokfvvnjrdmpvoxy";

        try {
            // Get the Session object.
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailFrom, password);
                }
            });

            // Compose the message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setText(message);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            // You may want to handle this exception differently, depending on your needs
            // For example, you might want to log the error, or display an error message to the user
        }
    }
}

