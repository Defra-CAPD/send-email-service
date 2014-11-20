package uk.gov.defra.capd.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.mail.model.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class SendEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailService.class);

    private final uk.gov.defra.capd.mail.configuration.SendEmailConfiguration sendEmailConfiguration;

    public SendEmailService(uk.gov.defra.capd.mail.configuration.SendEmailConfiguration sendEmailConfiguration) {
        this.sendEmailConfiguration = sendEmailConfiguration;
    }

    public void send(SimpleMailMessage email) throws MessagingException {
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", sendEmailConfiguration.getSmtpHost());
            properties.put("mail.smtp.port", sendEmailConfiguration.getSmtpPort());

            Session session = Session.getInstance(properties);
            Transport.send(MimeMessageBuilder.buildMessage(email, session));
        } catch (MessagingException mex) {
            LOGGER.error("Error trying to send email", mex);
            throw mex;
        }
    }
}
