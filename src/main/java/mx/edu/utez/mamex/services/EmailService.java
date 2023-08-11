package mx.edu.utez.mamex.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;



public class EmailService {

    public void sendEmail(String toEmail, String subject, String message) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        String mailFrom = "tiendamanosmexicanas1@gmail.com";
        String password = "eokfvvnjrdmpvoxy";

        try {

            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailFrom, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }
        public void sendEmailWithImage(String toEmail, String subject, String message, String imagePath) {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            // Set your email and password
            String mailFrom = "tiendamanosmexicanas1@gmail.com";
            String password = "eokfvvnjrdmpvoxy";

            try {
                Session session = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailFrom, password);
                    }
                });


                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(mailFrom));
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                mimeMessage.setSubject(subject);


                String htmlContent = "<html><body><p>" + message + "</p><img src='cid:image'></body></html>";
                MimeMultipart multipart = new MimeMultipart();
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlContent, "text/html");
                multipart.addBodyPart(htmlPart);

                attachImageToMessage(mimeMessage, multipart, imagePath);

                mimeMessage.setContent(multipart);

                // Send the message
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();

            }
        }

    private void attachImageToMessage(MimeMessage message, MimeMultipart multipart, String imagePath) throws MessagingException {
        // Create the image attachment part
        MimeBodyPart imagePart = new MimeBodyPart();
        DataSource source = new FileDataSource(imagePath);
        imagePart.setDataHandler(new DataHandler(source));
        imagePart.setFileName("MAMEX.jpg");
        imagePart.setContentID("<image>");
        multipart.addBodyPart(imagePart);

        message.setContent(multipart);
    }

    }



