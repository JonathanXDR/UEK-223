package ch.zli.m223.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Feedback;
import ch.zli.m223.service.ApplicationUserService;
import ch.zli.m223.service.FeedbackService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/feedbacks")
@Tag(name = "Feedbacks", description = "Handling of feedbacks")
@RolesAllowed({ "Member", "Admin" })
public class FeedbackController {

  @Inject
  FeedbackService feedbackService;

  @Inject
  ApplicationUserService userService;

  @Inject
  @RequestScoped
  SecurityContext ctx;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all feedbacks", description = "Returns all feedbacks")
  public List<Feedback> index() {
    return feedbackService.findAll();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get a feedback", description = "Return one feedback")
  @Path("/{id}")
  public Feedback show(@PathParam("id") Long id) {
    return feedbackService.findById(id);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Create a feedback", description = "Creates a new feedback")
  public Feedback create(@Valid Feedback feedback) {
    var user = userService.findByEmail(ctx.getUserPrincipal().getName());
    assert user.isPresent();
    feedback.setApplicationUser(user.get());
    return feedbackService.createFeedback(feedback);
  }
}
