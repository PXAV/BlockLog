package de.pxav.blocklog.database.repo;

import de.pxav.blocklog.model.InventorySession;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.entity.Player;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class InventorySessionRepository {

  protected SessionFactory sessionFactory;

  public InventorySessionRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public abstract InventorySession getById(int id);

  public abstract List<InventorySession> getByPlayerUUID(UUID uuid);

  public abstract List<InventorySession> getByLocation(SerialBlockLocation location);

  public abstract void save(InventorySession session);

  public abstract void delete(InventorySession session);

  public Collection<InventorySession> getByPlayer(Player player) {
    return getByPlayerUUID(player.getUniqueId());
  }

}
