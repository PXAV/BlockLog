package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.MaterialConverter;
import de.pxav.blocklog.database.converter.SerialBlockLocationConverter;
import de.pxav.blocklog.database.converter.SerialTimeConverter;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import de.pxav.blocklog.model.serial.SerialTime;
import org.bukkit.Material;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

  private String playerName;

  @Convert(converter = SerialBlockLocationConverter.class)
  private SerialBlockLocation blockLocation;

  @Convert(converter = MaterialConverter.class)
  private Material material;

  @Convert(converter = SerialTimeConverter.class)
  private SerialTime time;

  public ItemFrameInteraction(UUID playerUUID, String playerName, SerialBlockLocation blockLocation, Material material, SerialTime time) {
    this.playerUUID = playerUUID;
    this.playerName = playerName;
    this.blockLocation = blockLocation;
    this.material = material;
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

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public SerialTime getTime() {
    return time;
  }

  public void setTime(SerialTime time) {
    this.time = time;
  }

}
