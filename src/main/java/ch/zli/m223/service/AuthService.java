package ch.zli.m223.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Credential;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {

  @Inject
  ApplicationUserService applicationUserService;

  public Response authenticate(Credential credential) {
    Optional<ApplicationUser> principal = applicationUserService.findByEmail(
        credential.getEmail());

    try {
      if (principal.isPresent() &&
          principal.get().getPassword().equals(credential.getPassword())) {
        JwtClaimsBuilder jwt = Jwt
            .issuer("https://zli.example.com/")
            .upn(credential.getEmail())
            .groups(new HashSet<>(Arrays.asList("User", "Admin")))
            .expiresIn(Duration.ofHours(12));

        setRoles(jwt, principal.get());

        String token = jwt.sign();

        return Response
            .ok(principal.get())
            .cookie(new NewCookie.Builder("jwt").value(token).build())
            .header("Authorization", "Bearer " + token)
            .build();
      }
    } catch (Exception e) {
      System.err.println("Couldn't validate password");
    }

    return Response.status(Response.Status.FORBIDDEN).build();
  }

  private void setRoles(JwtClaimsBuilder jwt, ApplicationUser user) {
    List<String> roles = new ArrayList<>();
    switch (user.getRole()) {
      case ADMIN:
        roles.add("Admin");
      case MEMBER:
        roles.add("Member");
    }
    jwt.groups(new HashSet<>(roles));
  }
}
