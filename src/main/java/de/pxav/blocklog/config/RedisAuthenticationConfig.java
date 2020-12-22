package de.pxav.blocklog.config;

import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class RedisAuthenticationConfig extends BlockLogConfiguration {

  @Inject
  public RedisAuthenticationConfig(JavaPlugin javaPlugin) {
    super(javaPlugin);
  }

  @Override
  public File file() {
    return new File("plugins//BlockLog//redisAuthentication.yml");
  }

  @Override
  public String resourcePath() {
    return "redisAuthentication.yml";
  }

}
