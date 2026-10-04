package emailapi.EmailService.service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailSrevice {

	private final String from = "anmol77590@gmail.com";
	private final String password = "chrt jzex rxmd tvuh"; // Use App Password, not your Gmail password

	public boolean sendEmail(String to, String subject, String message) {

		try {
			// SMTP server configuration
			Properties props = new Properties();

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			// Authenticate sender
			Session session = Session.getInstance(props, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			});

			// Compose email
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			msg.setSubject(subject);
			msg.setText(message);

			// Send email
			Transport.send(msg);

			System.out.println("Email sent successfully to: " + to);

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;
		}
	}
}