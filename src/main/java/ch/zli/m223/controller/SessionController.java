package ch.zli.m223.controller;

import ch.zli.m223.model.Credential;
import ch.zli.m223.service.SessionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/login")
@Tag(name = "Auth", description = "Handling of authentication.")
public class SessionController {

  @Inject
  SessionService sessionService;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
    summary = "Allows the user to login.",
    description = "This endpoint allows the user to login. On successful login, a JWT (valid for 24 hours) will be returned in the Authorization header."
  )
  public Response login(Credential credential) {
    return sessionService.authenticate(credential);
  }
}
