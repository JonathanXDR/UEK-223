package ch.zli.m223.service;

import ch.zli.m223.model.Booking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class BookingService {

  @Inject
  EntityManager entityManager;

  public List<Booking> findAll() {
    var query = entityManager.createQuery("FROM Booking", Booking.class);
    return query.getResultList();
  }

  public Booking findById(long id) {
    var booking = entityManager.find(Booking.class, id);
    if (booking == null) {
      throw new NotFoundException();
    }
    return booking;
  }

  @Transactional
  public Booking createBooking(Booking booking) {
    entityManager.persist(booking);
    return booking;
  }

  @Transactional
  public Booking updateBooking(Booking newbooking) {
    entityManager.merge(newbooking);
    return newbooking;
  }

  @Transactional
  public void deleteBooking(Long id) {
    var booking = entityManager.find(Booking.class, id);
    if (booking == null) {
      throw new NotFoundException();
    }
    entityManager.remove(booking);
  }
}
