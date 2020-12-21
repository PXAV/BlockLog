package de.pxav.blocklog.database;

import de.pxav.blocklog.model.SerialBlockLocation;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SerialBlockLocationConverter implements AttributeConverter<SerialBlockLocation, String> {

  @Override
  public String convertToDatabaseColumn(SerialBlockLocation serialBlockLocation) {
    return serialBlockLocation.getWorld()
            + ";" + serialBlockLocation.getX()
            + ";" + serialBlockLocation.getY()
            + ";" + serialBlockLocation.getZ()
            + serialBlockLocation.getY() + serialBlockLocation.getZ();
  }

  @Override
  public SerialBlockLocation convertToEntityAttribute(String s) {
    String[] locationElements = s.split(";");
    return new SerialBlockLocation(
            locationElements[0],
            Integer.parseInt(locationElements[1]),
            Integer.parseInt(locationElements[2]),
            Integer.parseInt(locationElements[3]));
  }

}
