package neu.dtampubolon.connecteddevices.project;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SmtpConnector {

	private String username;
	private String password;
	private String recipient;
	private static Properties props;
	private static String host = "smtp.gmail.com";
	
	public SmtpConnector(String username, String password) {
		this.username = username;
		this.password = password;
		
		props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
	}
	
	public void sendMail(String to, String subject, String body) {
		sendMail(new String[] {to}, subject, body);
	}
	
	public void sendMail(String[] to, String subject, String body) {        
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(username));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			
			for(int i=0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
				System.out.println("Sending e-mail to " + to[i]);
			}
			
            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
                
            }
            
			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");			
			transport.connect(host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Email sent!");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

}
