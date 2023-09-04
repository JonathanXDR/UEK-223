package ch.zli.m223.service;

import java.util.List;
import java.util.Optional;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.RoleEnum;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ApplicationUserService {

  @Inject
  EntityManager entityManager;

  public List<ApplicationUser> findAll() {
    var query = entityManager.createQuery(
      "FROM ApplicationUser",
      ApplicationUser.class
    );
    return query.getResultList();
  }

  public ApplicationUser findById(Long id) {
    var user = entityManager.find(ApplicationUser.class, id);
    if (user == null) {
      throw new NotFoundException();
    }
    return user;
  }

  public Optional<ApplicationUser> findByEmail(String email) {
    var user = entityManager
      .createNamedQuery("ApplicationUser.findByEmail", ApplicationUser.class)
      .setParameter("email", email)
      .getResultStream()
      .findFirst();
    return user;
  }

  @Transactional
  public ApplicationUser createApplicationUser(
    ApplicationUser applicationUser
  ) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    cq.select(cb.count(cq.from(ApplicationUser.class)));
    var count = entityManager.createQuery(cq).getSingleResult();

    if (count == 0) {
      applicationUser.setRole(RoleEnum.ADMIN);
    } else {
      applicationUser.setRole(RoleEnum.MEMBER);
    }

    entityManager.persist(applicationUser);
    return applicationUser;
  }

  @Transactional
  public ApplicationUser updateApplicationUser(
    ApplicationUser newApplicationUser
  ) {
    entityManager.merge(newApplicationUser);
    return newApplicationUser;
  }

  @Transactional
  public void deleteApplicationUser(Long id) {
    var applicationUser = entityManager.find(ApplicationUser.class, id);
    if (applicationUser == null) {
      throw new NotFoundException();
    }
    applicationUser.getBookings().forEach(b -> entityManager.remove(b));
    entityManager.remove(applicationUser);
  }
}
