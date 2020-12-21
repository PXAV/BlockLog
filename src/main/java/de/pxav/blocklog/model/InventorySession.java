package de.pxav.blocklog.model;

import de.pxav.blocklog.database.SerialBlockLocationConverter;
import org.bukkit.event.inventory.InventoryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Entity
@Table(name = "inventory_sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventorySession {

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private int id;

  private UUID playerUUID;
  private String playerName;

  @Convert(converter = SerialBlockLocationConverter.class)
  private SerialBlockLocation blockLocation;

  private InventoryType inventoryType;

  public InventorySession() {}

  public InventorySession(UUID playerUUID, String playerName, SerialBlockLocation blockLocation, InventoryType inventoryType) {
    this.playerUUID = playerUUID;
    this.playerName = playerName;
    this.blockLocation = blockLocation;
    this.inventoryType = inventoryType;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public UUID getPlayerUUID() {
    return playerUUID;
  }

  public void setPlayerUUID(UUID playerUUID) {
    this.playerUUID = playerUUID;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public SerialBlockLocation getBlockLocation() {
    return blockLocation;
  }

  public void setBlockLocation(SerialBlockLocation blockLocation) {
    this.blockLocation = blockLocation;
  }

  public InventoryType getInventoryType() {
    return inventoryType;
  }

  public void setInventoryType(InventoryType inventoryType) {
    this.inventoryType = inventoryType;
  }

}
