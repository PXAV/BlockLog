package de.pxav.blocklog.database.converter;

import org.bukkit.event.inventory.InventoryType;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class InventoryTypeConverter implements AttributeConverter<InventoryType, String> {

  @Override
  public String convertToDatabaseColumn(InventoryType inventoryType) {
    return inventoryType.toString();
  }

  @Override
  public InventoryType convertToEntityAttribute(String s) {
    return InventoryType.valueOf(s);
  }

}
