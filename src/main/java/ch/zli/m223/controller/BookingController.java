package ch.zli.m223.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.StatusEnum;
import ch.zli.m223.service.ApplicationUserService;
import ch.zli.m223.service.BookingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
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
import jakarta.ws.rs.core.SecurityContext;

@Path("/bookings")
@Tag(name = "Bookings", description = "Handling of bookings")
@RolesAllowed({ "Member", "Admin" })
public class BookingController {

  @Inject
  BookingService bookingService;

  @Inject
  ApplicationUserService userService;

  @Inject
  @RequestScoped
  SecurityContext ctx;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get all bookings", description = "Returns all bookings")
  public List<Booking> index() {
    return bookingService.findAll();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get a booking", description = "Return one booking")
  @Path("/{id}")
  public Booking show(@PathParam("id") Long id) {
    return bookingService.findById(id);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Create a booking", description = "Creates a new booking")
  public Booking create(@Valid Booking booking) {
    var user = userService.findByEmail(ctx.getUserPrincipal().getName());
    assert user.isPresent();
    booking.setApplicationUser(user.get());
    booking.setStatus(StatusEnum.PENDING);
    return bookingService.createBooking(booking);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Update a booking", description = "Updates a booking")
  @Path("/{id}")
  public Booking update(@Valid Booking booking, @PathParam("id") Long id) {
    booking.setId(id);
    return bookingService.updateBooking(booking);
  }

  @DELETE
  @Operation(summary = "Delete a booking", description = "Deletes a booking")
  @Path("/{id}")
  public void delete(@PathParam("id") Long id) {
    bookingService.deleteBooking(id);
  }

}
