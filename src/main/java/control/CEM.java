package control;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// CEM = Classe de envio e e-mails
public class CEM {
	private static final String DE = "levros@zoho.com";
	private static final String SERVIDOR = "smtp.zoho.com";
	private static final String PORTA = "465";
	private static final String SENHA = "Iamyourf4ther!";
	
	
	public static void cadastro(String para, String user){
		Properties props = System.getProperties();
		props.put("mail.smtp.host", SERVIDOR);
		props.put("mail.smtp.port", PORTA);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(DE, SENHA);
					}
				  });
		try {
			Message  msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(DE, "Levros"));
			
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
			
			msg.setSubject("Bem vindo ao Levros!");
			msg.setText("Olá, " + user + "! Seu cadastro foi efetuado com sucesso.\nBem vindo ao menor banco de dados de livros da internet!");
			
			Transport.send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CEM.cadastro("mateusbandeiraa@gmail.com", "Mateus Bandeira");
	}
	
}
