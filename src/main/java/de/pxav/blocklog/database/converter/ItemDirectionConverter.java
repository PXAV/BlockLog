package de.pxav.blocklog.database.converter;

import de.pxav.blocklog.model.ItemDirection;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ItemDirectionConverter implements AttributeConverter<ItemDirection, String> {

  @Override
  public String convertToDatabaseColumn(ItemDirection itemDirection) {
    return itemDirection.toString();
  }

  @Override
  public ItemDirection convertToEntityAttribute(String s) {
    return ItemDirection.valueOf(s);
  }

}
