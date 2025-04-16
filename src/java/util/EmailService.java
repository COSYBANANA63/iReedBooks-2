package util;

import java.util.Properties;
import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    
    private static final String FROM_EMAIL = "noreply@ireedbooks.com";
    private static final String SMTP_HOST = "smtp.example.com";  // Replace with your SMTP server
    private static final String SMTP_USER = "your-username";     // Replace with your username
    private static final String SMTP_PASSWORD = "your-password"; // Replace with your password
    
    public static void sendOrderConfirmation(String toEmail, int transactionId, String customerName) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", "587");
            
            Session session = Session.getInstance(properties);
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("iReedBooks Order Confirmation #" + transactionId);
            
            String content = "Dear " + customerName + ",\n\n"
                + "Thank you for your order with iReedBooks!\n\n"
                + "Your order #" + transactionId + " has been successfully placed and is being processed.\n"
                + "You can view your order details by logging into your account.\n\n"
                + "Thank you for shopping with us!\n\n"
                + "Best regards,\n"
                + "The iReedBooks Team";
            
            message.setText(content);
            
            // Use SMTPTransport directly instead of Transport.send
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
            transport.connect(SMTP_HOST, SMTP_USER, SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            System.out.println("Email sent successfully to: " + toEmail);
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}