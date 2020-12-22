package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.InventorySession;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
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
public class InventorySessionRepository extends Repository<InventorySession> {

  private ExecutorService executorService;

  @Inject
  public InventorySessionRepository(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory, executorService);
  }

  public InventorySession getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (InventorySession) session
              .createCriteria(InventorySession.class)
              .add(Restrictions.eq("id", id))
              .uniqueResult();
    }
  }

  public List<InventorySession> getByPlayerUUID(UUID uuid) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(InventorySession.class)
              .add(Restrictions.eq("playerUUID", uuid))
              .list();
    }
  }

  public List<InventorySession> getByLocation(SerialBlockLocation location) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(InventorySession.class)
              .add(Restrictions.eq("blockLocation", location))
              .list();
    }
  }

}
