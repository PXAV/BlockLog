package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.BlacklistedBlock;
import de.pxav.blocklog.model.serial.SerialTime;
import org.bukkit.Material;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class BlacklistedBlockRepository extends Repository<BlacklistedBlock> {

  @Inject
  public BlacklistedBlockRepository(SessionFactory sessionFactory, ExecutorService executorService) {
    super(sessionFactory, executorService);
  }

  public BlacklistedBlock getById(int id) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (BlacklistedBlock) session.createCriteria(BlacklistedBlock.class).add(Restrictions.eq("id", id)).uniqueResult();
    }
  }

  public BlacklistedBlock getByMaterial(Material material) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (BlacklistedBlock) session.createCriteria(BlacklistedBlock.class).add(Restrictions.eq("material", material)).uniqueResult();
    }
  }

  public BlacklistedBlock getByMaterial(SerialTime since) {
    try (Session session = sessionFactory.getCurrentSession()) {
      session.beginTransaction();
      return (BlacklistedBlock) session.createCriteria(BlacklistedBlock.class).add(Restrictions.eq("since", since)).uniqueResult();
    }
  }

}
