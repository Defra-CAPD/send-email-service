package uk.gov.defra.capd.mail.health;

import com.google.common.base.Optional;
import com.yammer.metrics.core.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.common.logging.LogFormatter;
import uk.gov.defra.capd.mail.model.SimpleMailMessage;
import uk.gov.defra.capd.mail.service.SendEmailService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendEmailHealthCheck extends HealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailHealthCheck.class);
    private SendEmailService service;
    private String healthCheckAddress;

    public SendEmailHealthCheck(SendEmailService service, String healthCheckAddress) {
        super("sendEmail");
        this.service = service;
        this.healthCheckAddress = healthCheckAddress;
    }

    @Override
    protected Result check() throws Exception {
        try {
            service.send(newHealthCheckMessage());
            return Result.healthy();
        } catch (Exception e) {
            List<String> additionalData = new ArrayList<String>();
            additionalData.add(e.toString());
            LOGGER.error(LogFormatter.format(LogFormatter.Type.HEALTH,
                    LogFormatter.Severity.CRITICAL,
                    Optional.<String>absent(),
                    "Same as host", "SendEmailHealthCheck",
                    Optional.of(additionalData)));
            throw e;
        }
    }

    private SimpleMailMessage newHealthCheckMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(new String[]{healthCheckAddress});
        message.setSubject("Health Check: " + new Date());
        message.setText("This message is used to ensure the CAP Send Email Service can send E-mails");
        return message;
    }
}
