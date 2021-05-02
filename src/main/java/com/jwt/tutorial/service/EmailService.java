/*
* Enabling less secure apps to access Gmail
* Gmail considers regular email programs (like the one we're building) to be "less secure",
* so in order for our app to get access into your account, your "Allow less secure apps" option must be turned on
* on your Gmail account.
* If you don't allow sign-ins from less secure apps, the email won't be sent as the call to connect to
* your Gmail account will fail.
* Please see link below:
* https://hotter.io/docs/email-accounts/secure-app-gmail/
* Please note that allowing less secure apps can make it easier for hijackers to break into
* user accounts and devices. Blocking sign-ins from these apps helps keep accounts safe.
* It will be a good idea to turn it off after you're done testing.
* */


package com.jwt.tutorial.service;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static com.jwt.tutorial.constant.EmailConstant.*;

@Service
public class EmailService {

    public void sendNewPasswordEmail(String firstname, String password, String email){
        Message message = createEmail(firstname, password, email);
        try {
            SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
            smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message createEmail(String firstname, String password, String email){
        Message message = new MimeMessage(getEmailSession());
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CC_EMAIL, false));
            message.setSubject(EMAIL_SUBJECT);
            message.setText("Hello " + firstname + " \n \n Your new account password is: " + password + " \n \n The Support Team");
            message.setSentDate(new Date());
            message.saveChanges();
        } catch (MessagingException me){
            me.printStackTrace();
        }
        return message;
    }


    private Session getEmailSession(){
        Properties properties = System.getProperties();

        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}
