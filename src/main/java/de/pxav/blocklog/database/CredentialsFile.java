package de.pxav.blocklog.database;

import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class CredentialsFile {

  private String host;
  private int port;
  private String username;
  private String password;
  private String database;

  private File file;

  public CredentialsFile() { }

  public void createFile(String path, String fileName) {
    if (!fileName.endsWith(".yml")) {
      fileName = fileName + ".yml";
    }

    this.file = new File(path, fileName);
    File directory = new File(path);

    if (!directory.exists()) {
      directory.mkdirs();
    }

    if (!file.exists()) {
      try {
        if (file.createNewFile()) {
          this.writeDefaults();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public void writeDefaults() {
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
    configuration.set("host", "localhost");
    configuration.set("port", "3306");
    configuration.set("username", "username");
    configuration.set("password", "password");
    configuration.set("database", "database");
    try {
      configuration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadToCache() {
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    this.host = configuration.getString("host");
    this.port = configuration.getInt("port");
    this.username = configuration.getString("username");
    this.password = configuration.getString("password");
    this.database = configuration.getString("database");

  }

  public void saveToFile(File file) {
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    configuration.set("host", this.host);
    configuration.set("port", this.port);
    configuration.set("username", this.username);
    configuration.set("password", this.password);
    configuration.set("database", this.database);

    try {
      configuration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  String getHost() {
    return host;
  }

  int getPort() {
    return port;
  }

  String getUsername() {
    return username;
  }

  String getPassword() {
    return password;
  }

  String getDatabase() {
    return database;
  }

}
