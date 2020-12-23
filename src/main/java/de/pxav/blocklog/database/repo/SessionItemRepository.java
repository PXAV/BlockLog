package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.ItemDirection;
import de.pxav.blocklog.model.ItemFrameInteraction;
import de.pxav.blocklog.model.SessionItem;
import org.bukkit.Material;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SessionItemRepository extends Repository<SessionItem> {

  @Inject
  public SessionItemRepository(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory, executorService);
  }

  public SessionItem getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (SessionItem) session.createCriteria(SessionItem.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
  }

  public List<SessionItem> getByMaterial(Material material) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<SessionItem>) session.createCriteria(SessionItem.class).add(Restrictions.eq("material", material)).uniqueResult();
    }
  }

  public List<SessionItem> getByItemDirection(ItemDirection itemDirection) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<SessionItem>) session.createCriteria(SessionItem.class).add(Restrictions.eq("itemDirection", itemDirection)).uniqueResult();
    }
  }

  public List<SessionItem> getByAmount(int amount) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (List<SessionItem>) session.createCriteria(SessionItem.class).add(Restrictions.eq("amount", amount)).uniqueResult();
    }
  }

}
