package de.pxav.blocklog.database;

import org.bukkit.Material;

import javax.persistence.AttributeConverter;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MaterialConverter implements AttributeConverter<Material, String> {

  @Override
  public String convertToDatabaseColumn(Material material) {
    return material.toString();
  }

  @Override
  public Material convertToEntityAttribute(String s) {
    return Material.valueOf(s);
  }

}
