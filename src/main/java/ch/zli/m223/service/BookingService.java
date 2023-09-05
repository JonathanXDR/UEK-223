package ch.zli.m223.service;

import java.util.List;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class BookingService {

  @Inject
  EntityManager entityManager;

  public List<Booking> findAll() {
    var query = entityManager.createQuery("FROM Booking", Booking.class);
    if (query == null) {
      throw new NotFoundException("No bookings found");
    }
    return query.getResultList();
  }

  public List<Booking> findAllByUser(ApplicationUser applicationUser) {
    var query = entityManager
        .createQuery("FROM Booking WHERE applicationUser = :applicationUser", Booking.class)
        .setParameter("applicationUser", applicationUser);
    if (query == null) {
      throw new NotFoundException("No bookings found");
    }
    return query.getResultList();
  }

  public Booking findById(Long id) {
    var booking = entityManager.find(Booking.class, id);
    if (booking == null) {
      throw new NotFoundException("Booking not found");
    }
    return booking;
  }

  @Transactional
  public Booking createBooking(Booking booking) {
    return entityManager.merge(booking);
  }

  @Transactional
  public Booking updateBooking(Booking booking) {
    if (booking == null) {
      throw new NotFoundException("Booking not found");
    }
    return entityManager.merge(booking);
  }

  @Transactional
  public String deleteBooking(Long id) {
    var booking = entityManager.find(Booking.class, id);
    if (booking == null) {
      throw new NotFoundException("Booking not found");
    }
    entityManager.remove(booking);
    return "Booking deleted successfully";
  }
}
