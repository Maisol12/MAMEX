package mx.edu.utez.mamex.models.emails;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Email {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "mamex2023@gmail.com";
    private static final String SMTP_PASSWORD = "chimuelo2004";

    public void sendMail(String recipient, String subject, String content) throws MessagingException {
        // Configurar las propiedades del correo
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        // Crear el mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(content);

        // Enviar el mensaje
        Transport.send(message);
    }

}
