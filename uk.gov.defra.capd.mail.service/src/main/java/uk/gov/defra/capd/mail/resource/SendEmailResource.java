package uk.gov.defra.capd.mail.resource;

import com.yammer.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.defra.capd.mail.model.SimpleMailMessage;
import uk.gov.defra.capd.mail.service.SendEmailService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sendEmail")
@Produces(MediaType.APPLICATION_JSON)
public class SendEmailResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailResource.class);

    private final SendEmailService emailer;

    public SendEmailResource(SendEmailService emailer) {
        this.emailer = emailer;
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEmail(SimpleMailMessage email) {
        try{
            emailer.send(email);
            return Response.ok().build();
        }
        catch (Exception e) {
            LOGGER.error("Error sending email " + e);
            return Response.serverError().build();
        }
    }
}
