package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.MaterialConverter;
import de.pxav.blocklog.database.converter.SerialTimeConverter;
import de.pxav.blocklog.model.serial.SerialTime;
import org.bukkit.Material;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Entity
@Table(name = "blacklisted_blocks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlacklistedBlock {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  private int id;

  @Convert(converter = MaterialConverter.class)
  private Material material;

  @Convert(converter = SerialTimeConverter.class)
  private SerialTime since;

  public BlacklistedBlock(Material material, SerialTime since) {
    this.material = material;
    this.since = since;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public SerialTime getSince() {
    return since;
  }

  public void setSince(SerialTime since) {
    this.since = since;
  }

  public int getId() {
    return id;
  }
}
