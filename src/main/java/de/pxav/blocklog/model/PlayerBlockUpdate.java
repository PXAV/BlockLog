package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.MaterialConverter;
import de.pxav.blocklog.database.converter.SerialBlockLocationConverter;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name = "bl_player_block_updates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlayerBlockUpdate {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  private int id;

  @Type(type = "uuid-char")
  @Column(nullable = false)
  private UUID playerUUID;

  @Convert(converter = MaterialConverter.class)
  private Material fromMaterial;

  @Convert(converter = MaterialConverter.class)
  private Material toMaterial;

  private LocalDateTime serialTime;

  @Convert(converter = SerialBlockLocationConverter.class)
  private SerialBlockLocation location;

  public PlayerBlockUpdate(UUID playerUUID, Material fromMaterial, Material toMaterial, LocalDateTime serialTime, SerialBlockLocation location) {
    this.playerUUID = playerUUID;
    this.fromMaterial = fromMaterial;
    this.toMaterial = toMaterial;
    this.serialTime = serialTime;
    this.location = location;
  }

  public UUID getPlayerUUID() {
    return playerUUID;
  }

  public void setPlayerUUID(UUID playerUUID) {
    this.playerUUID = playerUUID;
  }

  public Material getFromMaterial() {
    return fromMaterial;
  }

  public void setFromMaterial(Material fromMaterial) {
    this.fromMaterial = fromMaterial;
  }

  public Material getToMaterial() {
    return toMaterial;
  }

  public void setToMaterial(Material toMaterial) {
    this.toMaterial = toMaterial;
  }

  public LocalDateTime getSerialTime() {
    return serialTime;
  }

  public void setSerialTime(LocalDateTime serialTime) {
    this.serialTime = serialTime;
  }

  public SerialBlockLocation getLocation() {
    return location;
  }

  public void setLocation(SerialBlockLocation location) {
    this.location = location;
  }

}
