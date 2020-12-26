package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.MaterialConverter;
import org.bukkit.Material;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Entity
@Table(name = "bl_blacklisted_blocks")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlacklistedBlock {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  @Column(unique = true, nullable = false)
  private int id;

  @Convert(converter = MaterialConverter.class)
  @Column(unique = true, nullable = false)
  private Material material;

  //@Convert(converter = DateConverter.class)
  private LocalDateTime since;

  private boolean allowBreaking;

  private boolean allowPlacing;

  private boolean allowInteracting;

  public BlacklistedBlock() {}

  public BlacklistedBlock(Material material, LocalDateTime since, boolean allowBreaking, boolean allowPlacing, boolean allowInteracting) {
    this.material = material;
    this.since = since;
    this.allowPlacing = allowPlacing;
    this.allowBreaking = allowBreaking;
    this.allowInteracting = allowInteracting;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public LocalDateTime getSince() {
    return since;
  }

  public void setSince(LocalDateTime since) {
    this.since = since;
  }

  public int getId() {
    return id;
  }

  public boolean breakingAllowed() {
    return allowBreaking;
  }

  public void allowBreaking() {
    this.allowBreaking = true;
  }

  public void disallowBreaking() {
    this.allowBreaking = false;
  }

  public boolean placingAllowed() {
    return this.allowPlacing;
  }

  public void allowPlacing() {
    this.allowPlacing = true;
  }

  public void disallowPlacing() {
    this.allowPlacing = false;
  }

  public boolean interactingAllowed() {
    return this.allowInteracting;
  }

  public void allowInteracting() {
    this.allowInteracting = true;
  }

  public void disallowInteracting() {
    this.allowInteracting = false;
  }

}
