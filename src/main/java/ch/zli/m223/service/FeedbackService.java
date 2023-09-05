package ch.zli.m223.service;

import java.util.List;

import ch.zli.m223.model.Feedback;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FeedbackService {

  @Inject
  EntityManager entityManager;

  public List<Feedback> findAll() {
    var query = entityManager.createQuery("FROM Feedback", Feedback.class);
    return query.getResultList();
  }

  public Feedback findById(Long id) {
    var feedback = entityManager.find(Feedback.class, id);
    if (feedback == null) {
      throw new NotFoundException();
    }
    return feedback;
  }

  @Transactional
  public Feedback createFeedback(Feedback feedback) {
    entityManager.persist(feedback);
    return feedback;
  }

  @Transactional
  public Feedback updateFeedback(Feedback feedback) {
    entityManager.merge(feedback);
    return feedback;
  }

  @Transactional
  public void deleteFeedback(Long id) {
    var feedback = entityManager.find(Feedback.class, id);
    if (feedback == null) {
      throw new NotFoundException();
    }
    entityManager.remove(feedback);
  }
}
