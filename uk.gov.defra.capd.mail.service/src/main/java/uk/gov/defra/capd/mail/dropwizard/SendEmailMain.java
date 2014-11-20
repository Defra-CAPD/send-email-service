package uk.gov.defra.capd.mail.dropwizard;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import uk.gov.defra.capd.common.healthcheck.ApplicationStatusService;
import uk.gov.defra.capd.mail.configuration.SendEmailConfiguration;
import uk.gov.defra.capd.mail.health.SendEmailHealthCheck;
import uk.gov.defra.capd.mail.resource.ApplicationStatusResource;
import uk.gov.defra.capd.mail.resource.SendEmailResource;
import uk.gov.defra.capd.mail.service.SendEmailService;

public class SendEmailMain extends Service<SendEmailConfiguration> {
    public static void main(String[] args) throws Exception {
        new SendEmailMain().run(args);
    }

    @Override
    public void initialize(Bootstrap<SendEmailConfiguration> bootstrap) {
        bootstrap.setName("sendEmailService");
    }

    @Override
    public void run(SendEmailConfiguration configuration,
                    Environment environment) {
        ApplicationStatusService applicationStatusService = new ApplicationStatusService();
        SendEmailService emailer = new SendEmailService(configuration);

        applicationStatusService.setApplicationVersionFilePath(configuration.getApplicationVersionFilePath());
        applicationStatusService.loadApplicationVersion("version.txt");

        environment.addResource(new ApplicationStatusResource(applicationStatusService));
        environment.addResource(new SendEmailResource(emailer));
        environment.addHealthCheck(new SendEmailHealthCheck(emailer, configuration.getHealthCheckEmailAddress()));
    }
}
