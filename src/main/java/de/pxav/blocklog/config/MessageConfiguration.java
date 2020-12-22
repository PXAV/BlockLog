package de.pxav.blocklog.config;

import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.io.File;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MessageConfiguration extends BlockLogConfiguration {

  @Inject
  public MessageConfiguration(JavaPlugin javaPlugin) {
    super(javaPlugin);
  }

  @Override
  public File file() {
    return new File("plugins//BlockLog//messages.yml");
  }

  @Override
  public String resourcePath() {
    return "messages.yml";
  }

}
