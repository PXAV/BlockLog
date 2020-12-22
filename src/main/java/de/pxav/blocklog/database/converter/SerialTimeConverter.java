package de.pxav.blocklog.database.converter;

import de.pxav.blocklog.model.serial.SerialTime;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SerialTimeConverter implements AttributeConverter<SerialTime, String> {

  @Override
  public String convertToDatabaseColumn(SerialTime serialTime) {
    return serialTime.getSecond()
            + ";" + serialTime.getMinute()
            + ";" + serialTime.getHour()
            + ";" + serialTime.getDayOfMonth()
            + ";" + serialTime.getMonthOfYear()
            + ";" + serialTime.getYear();
  }

  @Override
  public SerialTime convertToEntityAttribute(String s) {
    String[] timeElements = s.split(";");
    return new SerialTime(Integer.parseInt(timeElements[0]),
            Integer.parseInt(timeElements[1]),
            Integer.parseInt(timeElements[2]),
            Integer.parseInt(timeElements[3]),
            Integer.parseInt(timeElements[4]),
            Integer.parseInt(timeElements[5]));
  }

}
