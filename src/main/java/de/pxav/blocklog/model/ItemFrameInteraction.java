package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.MaterialConverter;
import de.pxav.blocklog.database.converter.SerialBlockLocationConverter;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Entity
@Table(name = "bl_item_frame_interactions")
public class ItemFrameInteraction {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  private int id;

  @Type(type = "uuid-char")
  @Column(nullable = false)
  private UUID playerUUID;

  @Convert(converter = SerialBlockLocationConverter.class)
  private SerialBlockLocation blockLocation;

  @Convert(converter = MaterialConverter.class)
  private Material material;

  private ItemDirection action;

  private LocalDateTime time;

  public ItemFrameInteraction(UUID playerUUID, SerialBlockLocation blockLocation, Material material, ItemDirection action, LocalDateTime time) {
    this.playerUUID = playerUUID;
    this.blockLocation = blockLocation;
    this.material = material;
    this.action = action;
    this.time = time;
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

  public SerialBlockLocation getBlockLocation() {
    return blockLocation;
  }

  public void setBlockLocation(SerialBlockLocation blockLocation) {
    this.blockLocation = blockLocation;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public ItemDirection getAction() {
    return action;
  }

  public void setAction(ItemDirection action) {
    this.action = action;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

}
