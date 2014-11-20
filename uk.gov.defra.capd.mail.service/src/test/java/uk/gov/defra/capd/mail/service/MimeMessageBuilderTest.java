package uk.gov.defra.capd.mail.service;

import org.junit.Test;
import uk.gov.defra.capd.mail.model.SimpleMailMessage;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

public class MimeMessageBuilderTest {

    @Test
    public void buildMessage_whenNoRecipientsProvided_shouldThrowException() {
        final  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("test@test.com");
        simpleMailMessage.setSubject("Test Subject");
        simpleMailMessage.setText("Test text");

        try {
            final  Properties properties = System.getProperties();
            final Session session = Session.getInstance(properties);
            MimeMessageBuilder.buildMessage(simpleMailMessage, session);
            fail("Exception should have been thrown");
        } catch (MessagingException messagingException) {
            assertThat(messagingException.getMessage()).isEqualTo("Email needs one or more recipients");
        }
    }

    @Test
    public void buildMessage_whenBlankEmailAddressProvided_shouldThrowException() {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("test@test.com");
        simpleMailMessage.setSubject("Test Subject");
        simpleMailMessage.setText("Test text");
        String[] toAddresses = {""};
        simpleMailMessage.setTo(toAddresses);

        try {
            final Properties properties = System.getProperties();
            final Session session = Session.getInstance(properties);
            MimeMessageBuilder.buildMessage(simpleMailMessage, session);
            fail("Exception should have been thrown");
        } catch (MessagingException messagingException) {
            assertThat(messagingException.getMessage()).isEqualTo("Illegal address");
        }
    }

    @Test
    public void buildMessage_whenValidEmailObjectProvided_shouldReturnCorrectObject() {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("test@test.com");
        simpleMailMessage.setSubject("Test Subject");
        simpleMailMessage.setText("Test text");
        String[] toAddresses = {"test@test.com", "test2@test2.com"};
        simpleMailMessage.setTo(toAddresses);
        String[] bccAddresses = {"testbcc@test.com", "test2bcc@test2.com", "test3bcc@test3.com"};
        simpleMailMessage.setBcc(bccAddresses);

        try {
            final Properties properties = System.getProperties();
            final Session session = Session.getInstance(properties);
            final MimeMessage mimeMessage = MimeMessageBuilder.buildMessage(simpleMailMessage, session);

            Address[] fromAddresses = mimeMessage.getFrom();
            assertThat(fromAddresses.length).isEqualTo(1);
            assertThat(fromAddresses[0].toString()).isEqualTo("test@test.com");
            assertThat(mimeMessage.getSubject()).isEqualTo("Test Subject");
            try {
                assertThat((String) mimeMessage.getContent()).isEqualTo("Test text");
            } catch (IOException io) {
                fail("Exception trying to read mime message content");
            }
            Address[] mimeMessageToAddresses = mimeMessage.getRecipients(Message.RecipientType.TO);
            assertThat(mimeMessageToAddresses.length).isEqualTo(2);
            assertThat(mimeMessageToAddresses[0].toString()).isEqualTo("test@test.com");
            assertThat(mimeMessageToAddresses[1].toString()).isEqualTo("test2@test2.com");
            Address[] mimeMessagBCCAddresses = mimeMessage.getRecipients(Message.RecipientType.BCC);
            assertThat(mimeMessagBCCAddresses.length).isEqualTo(3);
            assertThat(mimeMessagBCCAddresses[0].toString()).isEqualTo("testbcc@test.com");
            assertThat(mimeMessagBCCAddresses[1].toString()).isEqualTo("test2bcc@test2.com");
            assertThat(mimeMessagBCCAddresses[2].toString()).isEqualTo("test3bcc@test3.com");


        } catch (MessagingException messagingException) {
            fail("Messaging exception thrown while reading properties of tested object");
        }
    }

}
