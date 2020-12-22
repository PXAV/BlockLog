package de.pxav.blocklog.config;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class BlockLogConfiguration {

  protected Map<String, Object> cachedValues = Maps.newHashMap();

  private File file;
  private YamlConfiguration configuration;

  private JavaPlugin javaPlugin;

  BlockLogConfiguration(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
  }

  public abstract File file();

  public abstract String resourcePath();

  public void createFileIfNotExists() {
    this.file = file();

    if (file.exists()) {
      return;
    }

    try {
      FileUtils.copyToFile(javaPlugin.getResource(this.resourcePath()), this.file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void cacheConfig() {
    this.configuration = YamlConfiguration.loadConfiguration(file);
    this.configuration.getKeys(true).forEach(current
            -> cachedValues.put(current, configuration.get(current)));
  }

  public Object getValue(String key) {
    return this.cachedValues.get(key);
  }

  public String getString(String key) {
    return String.valueOf(this.cachedValues.get(key));
  }

  public int getInteger(String key) {
    return Integer.parseInt(this.getString(key));
  }

  public boolean getBoolean(String key) {
    return Boolean.parseBoolean(this.getString(key));
  }

}
