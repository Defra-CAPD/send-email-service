package uk.gov.defra.capd.mail.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SendEmailConfiguration extends Configuration {
    @NotEmpty
    @JsonProperty
    private String smtpHost;

    @Min(1)
    @Max(65535)
    @JsonProperty
    private int smtpPort;

    @NotEmpty
    @JsonProperty
    private String healthCheckEmailAddress;

    @JsonProperty
    private String applicationVersionFilePath;

    public String getSmtpHost() {
        return smtpHost;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public String getHealthCheckEmailAddress() {
        return healthCheckEmailAddress;
    }

    public String getApplicationVersionFilePath() {
        return applicationVersionFilePath;
    }
}