package de.pxav.blocklog.database.converter;

import de.pxav.blocklog.model.BlockUpdateType;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class BlockUpdateTypeConverter implements AttributeConverter<BlockUpdateType, String> {

  @Override
  public String convertToDatabaseColumn(BlockUpdateType blockUpdateType) {
    return blockUpdateType.toString();
  }

  @Override
  public BlockUpdateType convertToEntityAttribute(String s) {
    return BlockUpdateType.valueOf(s);
  }

}
