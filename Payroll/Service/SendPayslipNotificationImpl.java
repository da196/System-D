package com.Hrms.Payroll.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.Entity.HrmsPayroll;
import com.Hrms.Payroll.Repository.HrmsPayrollRepository;

@Service
public class SendPayslipNotificationImpl implements SendPayslipNotification {

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private HrmsPayrollRepository hrmsPayrollRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<?> sendPaySlipNotification(String slipdate) {
		LocalDate slipdatel = LocalDate.parse(slipdate);

		int year = slipdatel.getYear();
		Month month = slipdatel.getMonth();
		int day = slipdatel.getDayOfMonth();
		int monthvalue = slipdatel.getMonthValue();
		List<String> emails = new ArrayList<String>();

		List<String> emailstest = new ArrayList<String>();

		// email subject

		String subject = "PAY SLIP NOTIFICATION FOR THE MONTH OF " + month + " " + year;

		// message which is to be sent
		// String message = "This is to notify you formally that, your NET SALARY for
		// the month of " + month + " " + year
		// + " has been deposited to your respective account/s since " + day + " " +
		// month + " " + year;

		String message = "Kindly be informed that, your " + month + " " + year
				+ " pay has been successfully processed and a new pay slip is available.";

		List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, monthvalue, 1);

		for (HrmsPayroll dbm : dbms) {

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

			String mail = null;
			String name = "";

			if (hrmsEmployee != null && hrmsEmployee.getEmail() != null) {
				mail = hrmsEmployee.getEmail();
				StringBuilder fullname = new StringBuilder();

				fullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullname.append(" " + hrmsEmployee.getLastName().trim());
				name = fullname.toString();
			}
			if (mail != null) {
				emails.add(mail);

				// sendSingleEmail(subject, "john.cornery@tcra.go.tz", message, slipdate, name);
			}

		}

		// sendSingleEmail(subject, "john.cornery@tcra.go.tz", message, slipdate, "JOHN
		// BUHABI");
		emailstest.add("john.cornery@tcra.go.tz");
		emailstest.add("agape.kamagenge@tcra.go.tz");
		emailstest.add("jacqueline.kipangula@tcra.go.tz");

		// send the email to multiple recipients

		sendBulkEmail(subject, emailstest, message, slipdate);

		return null;
	}

	public static void sendBulkEmail(final String subject, final List<String> emailToAddresses,
			final String emailBodyText, String slipdate) {

		LocalDate slipdatel = LocalDate.parse(slipdate);

		// email subject

		int year = slipdatel.getYear();
		Month month = slipdatel.getMonth();
		int day = slipdatel.getDayOfMonth();

		// from email address
		final String username = "developers@tcra.go.tz";

		// make sure you put your correct password
		final String password = "your email password";

		// smtp email server
		final String smtpHost = "10.200.221.19";

		// We will put some properties for smtp configurations
		Properties props = new Properties();

		// do not change - start
		props.put("mail.smtp.user", "username");
		props.put("mail.smtp.host", smtpHost);
		// props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "false");
		// do not change - end

		// we authentcate using your email and password and on successful
		// we create the session
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String emails = null;

		try {
			// we create new message
			Message message = new MimeMessage(session);

			// set the from 'email address'
			message.setFrom(new InternetAddress(username));

			// set email subject
			message.setSubject(subject);

			// set email message
			// this will send html mail to the intended recipients
			// if you do not want to send html mail then you do not need to wrap the message
			// inside html tags
			String content = "<html>\n<body>\n";
			// content += emailBodyText + "\n";

			content += " <b>Dear All</b> &#013 </hr>"
					+ "This is to notify you formally  that, your NET SALARY for the month of  " + month + " " + year
					+ "</br> has been deposited to your respective account/s since " + day + " " + month + " " + year
					+ "</br>" + ".\r\n" + "\r\n" + "Regards\r\n" + "\r\n" + "</br>" + "PHRAO\r\n" + "</br>"
					+ "for D/CRM";
			content += "\n";
			content += "</body>\n</html>";
			message.setContent("<h4>Dear All, <br>" + "<br>" + emailBodyText + "<br>" + "<br>"
					+ " Log in to HRMS Self Sevice to view all your pay slips." + "<br>" + "Regards," + "<br>" + "PHRAO"
					+ "<br>" + "for D/CRM" + "</h4>", "text/html");

			// form all emails in a comma separated string
			StringBuilder sb = new StringBuilder();

			int i = 0;
			for (String email : emailToAddresses) {
				sb.append(email);
				i++;
				if (emailToAddresses.size() > i) {
					sb.append(", ");
				}
			}

			emails = sb.toString();

			// set 'to email address'
			// you can set also CC or TO for recipient type
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString()));

			System.out.println("Sending Email to " + emails + " from " + username + " with Subject - " + subject);

			// send the email
			Transport.send(message);

			System.out.println("Email successfully sent to " + emails);
		} catch (MessagingException e) {
			System.out.println("Email sending failed to " + emails);
			System.out.println(e);
		}
	}

	public static void sendSingleEmail(final String subject, final String emailToAddress, final String emailBodyText,
			String slipdate, String receivername) {

		LocalDate slipdatel = LocalDate.parse(slipdate);

		// email subject

		int year = slipdatel.getYear();
		Month month = slipdatel.getMonth();
		int day = slipdatel.getDayOfMonth();

		// from email address
		final String from = "developers@tcra.go.tz";

		// make sure you put your correct password
		final String password = "your email password";

		// smtp email server
		final String host = "10.200.221.19";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailToAddress));

			message.setSubject("Hrms Login Notification");
			// message.setText("Hello " + username + " You have successfully logged in the
			// Hrms System");
			message.setContent("<h4>Dear " + receivername + ", <br>" + "<br>" + emailBodyText + "<br>" + "<br>"
					+ " Log in to HRMS Self Sevice to view all your pay slips." + "<br>" + "Regards," + "<br>" + "PHRAO"
					+ "<br>" + "for D/CRM" + "</h4>", "text/html");
			Transport.send(message);
			System.out.println("message sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();

		}

	}

}
