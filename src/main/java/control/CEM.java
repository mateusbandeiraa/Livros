package control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.regex.Matcher;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.TicketSenha;




// CEM = Classe de envio e e-mails
public class CEM {
	private static final String DE = "levros@zoho.com";
	private static final String SERVIDOR = "smtp.zoho.com";
	private static final String PORTA = "465";
	private static final String SENHA = "Iamyourf4ther!";
	
	
	public static void resgateSenha(TicketSenha ticket){
		String link;
		String htmlMsg = "";
		try {
			htmlMsg = new CEM().arquivo("/mail/redefine-senha.html");
			link = "http://187.105.35.16:8081/livros/resetsenha.jsp?code="+URLEncoder.encode(ticket.getTicketPass(), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		htmlMsg = htmlMsg.replaceAll("%link%", Matcher.quoteReplacement(link));
		enviar("Redefinição de senha", ticket.getUsuario().getEmail(), htmlMsg);
	}
	
	public static void cadastro(String para, String user){
		String htmlMsg = "";
		try {
			htmlMsg = new CEM().arquivo("/mail/new-user.html");
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
		
		htmlMsg = htmlMsg.replaceAll("%user%", user);
		enviar("Bem vindo ao Levros!", para, htmlMsg);
	}
	
	private static void enviar(String subj, String para, String txtMsg){
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
			
			msg.setSubject(subj);
			
			msg.setContent(txtMsg, "text/html");
			
			Transport.send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String arquivo(String caminho) throws IOException{
		InputStream is = this.getClass().getResourceAsStream(caminho);
		
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}
	
}
