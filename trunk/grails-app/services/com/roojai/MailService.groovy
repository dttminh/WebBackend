package com.roojai

import grails.gorm.transactions.Transactional

import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

@Transactional
class MailService {
	//https://docs.microsoft.com/en-us/azure/store-sendgrid-java-how-to-send-email#bkmk_HowToUseJavax
	def SystemconfigurationService
	Session mailSession =null 
    def send(String from,String to,String subject,String content) {
		this.createMailSession();
		def emailList =  to.split(",")
		MimeMessage message = new MimeMessage(mailSession);
		
		Multipart multipart = new MimeMultipart("alternative");
		BodyPart part1 = new MimeBodyPart();
		part1.setContent(content,"text/html; charset=UTF-8");
		multipart.addBodyPart(part1);
		
		message.setFrom(new InternetAddress(from));
		for(String email:emailList){
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
		}
		message.setSubject(subject);
		message.setContent(multipart);
		
		Transport transport = mailSession.getTransport();
		// Connect the transport object.
		transport.connect();
		// Send the message.
		transport.sendMessage(message, message.getAllRecipients());
		// Close the connection.
		transport.close();
    }
	def createMailSession(){
		if( mailSession==null ){
			String SMTP_HOST_NAME = SystemconfigurationService.getValueByKey("mail.SMTP_HOST_NAME");
			String SMTP_PORT = SystemconfigurationService.getValueByKey("mail.SMTP_PORT");
			String SMTP_AUTH_USER = SystemconfigurationService.getValueByKey("mail.SMTP_AUTH_USER");
			String SMTP_AUTH_PWD = SystemconfigurationService.getValueByKey("mail.SMTP_AUTH_PWD");
			
			Properties properties = new Properties();
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.host", SMTP_HOST_NAME);
			properties.put("mail.smtp.port", SMTP_PORT);
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable","true");
			
			Authenticator auth = new SMTPAuthenticator(SMTP_AUTH_USER,SMTP_AUTH_PWD);
			mailSession = Session.getDefaultInstance(properties,auth);
		}
	}
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		String username;
		String password;
		public SMTPAuthenticator(String username,String password){
			this.username=username;
			this.password=password;
		}
		public PasswordAuthentication getPasswordAuthentication() {
		   return new PasswordAuthentication(username, password);
		}
	}
}
