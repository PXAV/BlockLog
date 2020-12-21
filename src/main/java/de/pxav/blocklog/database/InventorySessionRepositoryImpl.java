package de.pxav.blocklog.database;

import de.pxav.blocklog.model.InventorySession;
import de.pxav.blocklog.model.SerialBlockLocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class InventorySessionRepositoryImpl extends InventorySessionRepository {

  private ExecutorService executorService;

  @Inject
  public InventorySessionRepositoryImpl(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory);
    this.executorService = executorService;
  }

  @Override
  public InventorySession getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (InventorySession) session
              .createCriteria(InventorySession.class)
              .add(Restrictions.eq("id", id))
              .uniqueResult();
    }
  }

  @Override
  public List<InventorySession> getByPlayerUUID(UUID uuid) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(InventorySession.class)
              .add(Restrictions.eq("playerUUID", uuid))
              .list();
    }
  }

  @Override
  public List<InventorySession> getByLocation(SerialBlockLocation location) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(InventorySession.class)
              .add(Restrictions.eq("blockLocation", location))
              .list();
    }
  }

  @Override
  public void save(InventorySession inventorySession) {
    executorService.execute(() -> {
      try (Session session = this.sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(inventorySession);
        transaction.commit();
      }
    });
  }

  @Override
  public void delete(InventorySession inventorySession) {
    executorService.execute(() -> {
      try (Session session = this.sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.delete(inventorySession);
        transaction.commit();
      }
    });
  }

}
