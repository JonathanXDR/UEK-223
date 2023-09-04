package ch.zli.m223.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Credential;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class SessionService {

  @Inject
  ApplicationUserService applicationUserService;

  public Response authenticate(Credential credential) {
    Optional<ApplicationUser> principal = applicationUserService.findByEmail(
      credential.getEmail()
    );

    try {
      if (
        principal.isPresent() &&
        principal.get().getPassword().equals(credential.getPassword())
      ) {
        String token = Jwt
          .issuer("https://zli.example.com/")
          .upn(credential.getEmail())
          .groups(new HashSet<>(Arrays.asList("User", "Admin")))
          .expiresIn(Duration.ofHours(12))
          .sign();

        return Response
          .ok(principal.get())
          .cookie(new NewCookie.Builder("jwt").value(token).build())
          .header("Authorization", "Bearer " + token)
          .build();
      }
    } catch (Exception e) {
      System.err.println("Couldn't validate password.");
    }

    return Response.status(Response.Status.FORBIDDEN).build();
  }
}
