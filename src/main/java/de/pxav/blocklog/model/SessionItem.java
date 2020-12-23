package de.pxav.blocklog.model;

import de.pxav.blocklog.database.converter.ItemDirectionConverter;
import de.pxav.blocklog.database.converter.MaterialConverter;
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
@Table(name = "bl_session_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SessionItem {

  @Id
  @GeneratedValue(generator = "increment", strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "increment", strategy = "increment")
  private Integer id = null;

  @ManyToOne(fetch = FetchType.EAGER)
  private InventorySession session;

  @Convert(converter = MaterialConverter.class)
  private Material material;

  private int amount;

  @Convert(converter = ItemDirectionConverter.class)
  private ItemDirection itemDirection;

  public SessionItem() {}

  public SessionItem(InventorySession session, Material material, int amount, ItemDirection itemDirection) {
    this.session = session;
    this.material = material;
    this.itemDirection = itemDirection;
    this.amount = amount;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public InventorySession getSession() {
    return session;
  }

  public void setSession(InventorySession session) {
    this.session = session;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public ItemDirection getItemDirection() {
    return itemDirection;
  }

  public void setItemDirection(ItemDirection itemDirection) {
    this.itemDirection = itemDirection;
  }

}
