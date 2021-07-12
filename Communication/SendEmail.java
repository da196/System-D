package com.Hrms.Communication;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class SendEmail {
	public String sendmaillogin(String username, String usermail, String messages) {

		String to = username + "<" + usermail + ">"; // change accordingly
		String from = "developers@tcra.go.tz";// change accordingly developers@tcra.go.tz

		String host = "10.200.221.19";// or IP address

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Hrms Notification");
			// message.setText("Hello " + username + " You have successfully logged in the
			// Hrms System");
			message.setContent("<h1>Hello " + username + "<br>" + messages + "</h1>", "text/html");
			Transport.send(message);
			System.out.println("message sent successfully....");

			return "email has been sent";

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "email was not  sent";
		}
	}

	public String sendmailNotification(String username, String usermail, String messages) {

		String to = username + "<" + usermail + ">"; // change accordingly
		String from = "developers@tcra.go.tz";// change accordingly developers@tcra.go.tz

		String host = "10.200.221.19";// or IP address

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Hrms Login Notification");
			// message.setText("Hello " + username + " You have successfully logged in the
			// Hrms System");
			message.setContent("<h1>Hello " + username + "<br>" + messages + "</h1>", "text/html");
			Transport.send(message);
			System.out.println("message sent successfully....");

			return "email has been sent";

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "email was not  sent";
		}
	}

	public String NotificationReminderOnEmploymentCategory(String username, String usermail, String messages) {

		String to = username + "<" + "developers@tcra.go.tz" + ">"; // change accordingly
		String from = "developers@tcra.go.tz";// change accordingly developers@tcra.go.tz

		String host = "10.200.221.19";// or IP address

		// Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Hrms EmploymentCategoryChange Notification");
			// message.setText("Hello " + username + " You have successfully logged in the
			// Hrms System");
			message.setContent("<h1>Hello " + username + "<br>" + messages + "</h1>", "text/html");
			Transport.send(message);
			System.out.println("message sent successfully....");

			return "email has been sent";

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "email was not  sent";
		}
	}

}
