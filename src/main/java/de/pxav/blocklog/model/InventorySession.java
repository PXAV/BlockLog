package de.pxav.blocklog.model;

import com.google.common.collect.Sets;
import de.pxav.blocklog.database.converter.InventoryTypeConverter;
import de.pxav.blocklog.database.converter.SerialBlockLocationConverter;
import de.pxav.blocklog.database.converter.SerialTimeConverter;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import de.pxav.blocklog.model.serial.SerialTime;
import org.bukkit.event.inventory.InventoryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Entity
@Table(name = "bl_inventory_sessions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventorySession {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  private int id;

  @Type(type = "uuid-char")
  @Column(nullable = false)
  private UUID playerUUID;

  private String playerName;

  @Convert(converter = SerialTimeConverter.class)
  private SerialTime sessionStart;

  @Convert(converter = SerialTimeConverter.class)
  private SerialTime sessionEnd;

  @Convert(converter = SerialBlockLocationConverter.class)
  @Column(nullable = false)
  private SerialBlockLocation blockLocation;

  @Convert(converter = InventoryTypeConverter.class)
  @Column(nullable = false)
  private InventoryType inventoryType;

  @OneToMany(cascade = CascadeType.ALL, mappedBy="session", orphanRemoval = true)
  private Set<SessionItem> items = Sets.newHashSet();

  public InventorySession() {}

  public InventorySession(UUID playerUUID, String playerName, SerialTime sessionStart, SerialBlockLocation blockLocation, InventoryType inventoryType) {
    this.playerUUID = playerUUID;
    this.playerName = playerName;
    this.sessionStart = sessionStart;
    this.blockLocation = blockLocation;
    this.inventoryType = inventoryType;
  }

  public InventorySession(UUID playerUUID, String playerName, SerialTime sessionStart, SerialTime sessionEnd, SerialBlockLocation blockLocation, InventoryType inventoryType) {
    this.playerUUID = playerUUID;
    this.playerName = playerName;
    this.sessionStart = sessionStart;
    this.sessionEnd = sessionEnd;
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

  public SerialTime getSessionStart() {
    return sessionStart;
  }

  public void setSessionStart(SerialTime sessionStart) {
    this.sessionStart = sessionStart;
  }

  public SerialTime getSessionEnd() {
    return sessionEnd;
  }

  public void setSessionEnd(SerialTime sessionEnd) {
    this.sessionEnd = sessionEnd;
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

  public void addItem(SessionItem sessionItem) {
    this.items.add(sessionItem);
  }

  public Set<SessionItem> getItems() {
    return items;
  }

}
