package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.BlacklistedBlock;
import de.pxav.blocklog.model.PlayerBlockUpdate;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class PlayerBlockUpdateRepository extends Repository<PlayerBlockUpdate> {

  @Inject
  public PlayerBlockUpdateRepository(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory, executorService);
  }

  public PlayerBlockUpdate getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (PlayerBlockUpdate) session.createCriteria(PlayerBlockUpdate.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
  }

  public List<PlayerBlockUpdate> getByLocation(SerialBlockLocation location) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<PlayerBlockUpdate>) session.createCriteria(PlayerBlockUpdate.class).add(Restrictions.eq("location", location)).list();
    }
  }

  public List<PlayerBlockUpdate> getByPlayer(UUID playerUUID) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<PlayerBlockUpdate>) session.createCriteria(PlayerBlockUpdate.class).add(Restrictions.eq("playerUUID", playerUUID.toString())).list();
    }
  }

}
