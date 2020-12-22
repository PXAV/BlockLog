package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.InventorySession;
import de.pxav.blocklog.model.ItemFrameInteraction;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
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
public class ItemFrameInteractionRepository extends Repository<ItemFrameInteraction> {

  @Inject
  public ItemFrameInteractionRepository(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory, executorService);
  }

  public ItemFrameInteraction getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (ItemFrameInteraction) session.createCriteria(ItemFrameInteraction.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
  }

  public List<ItemFrameInteraction> getByPlayerUUID(UUID playerUUID) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(ItemFrameInteraction.class)
              .add(Restrictions.eq("playerUUID", playerUUID))
              .list();
    }
  }

  public List<ItemFrameInteraction> getByLocation(SerialBlockLocation blockLocation) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(ItemFrameInteraction.class)
              .add(Restrictions.eq("blockLocation", blockLocation))
              .list();
    }
  }

  public List<ItemFrameInteraction> getByMaterial(Material material) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return session.createCriteria(ItemFrameInteraction.class)
              .add(Restrictions.eq("material", material))
              .list();
    }
  }

}
