package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.BlacklistedBlock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class Repository<T> {

  protected SessionFactory sessionFactory;
  protected ExecutorService executorService;

  public Repository(SessionFactory sessionFactory, ExecutorService executorService) {
    this.sessionFactory = sessionFactory;
    this.executorService = executorService;
  }

  public List<T> findAll(Class<T> t) {
    try (Session session = this.sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<T>) session.createCriteria(t).list();
    }
  }

  public void save(T t) {
    executorService.execute(() -> {
      try (Session session = this.sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(t);
        transaction.commit();
      }
    });
  }

  public void delete(T t) {
    executorService.execute(() -> {
      try (Session session = this.sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
      }
    });
  }

}
