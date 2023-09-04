package ch.zli.m223.controller;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.service.ApplicationUserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/users")
@Tag(name = "Users", description = "Handling of users.")
public class ApplicationUserController {

  @Inject
  ApplicationUserService applicationUserService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all users.", description = "Returns all users.")
  @RolesAllowed("admin")
  public List<ApplicationUser> index() {
    return applicationUserService.findAll();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @PermitAll
  @Operation(summary = "Create a user.", description = "Creates a user.")
  public ApplicationUser create(@Valid ApplicationUser applicationUser) {
    applicationUser.setPassword(applicationUser.getPassword());
    return applicationUserService.createApplicationUser(applicationUser);
  }

  @DELETE
  @Operation(summary = "Delete a user.", description = "Deletes a user.")
  @Path("/{id}")
  @RolesAllowed("admin")
  public void delete(@PathParam("id") long id) {
    applicationUserService.deleteApplicationUser(id);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update a user.", description = "Updates a user.")
  @Path("/{id}")
  @RolesAllowed("admin")
  public ApplicationUser update(
    @Valid ApplicationUser applicationUser,
    @PathParam("id") long id
  ) {
    applicationUser.setPassword(applicationUser.getPassword());
    applicationUser.setId(id);
    return applicationUserService.updateApplicationUser(applicationUser);
  }
}
