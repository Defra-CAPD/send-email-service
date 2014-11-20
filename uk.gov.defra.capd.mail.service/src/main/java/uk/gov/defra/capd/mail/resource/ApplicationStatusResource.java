package uk.gov.defra.capd.mail.resource;

import uk.gov.defra.capd.common.healthcheck.ApplicationStatusService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/status")
public class ApplicationStatusResource {

    private ApplicationStatusService applicationStatusService;

    public ApplicationStatusResource(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        return applicationStatusService.getStatus("capd-send-email-service");

    }

    public void setApplicationStatusService(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }
}
