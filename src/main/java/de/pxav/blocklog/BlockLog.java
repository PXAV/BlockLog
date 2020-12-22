package de.pxav.blocklog;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.blocklog.config.BlockLogConfiguration;
import de.pxav.blocklog.config.MessageConfiguration;
import de.pxav.blocklog.config.SettingsConfiguration;
import de.pxav.blocklog.database.CredentialsFile;
import de.pxav.blocklog.database.InventorySessionRepository;
import de.pxav.blocklog.database.TableCreator;
import de.pxav.blocklog.inject.SimpleBinderModule;
import de.pxav.blocklog.model.InventorySession;
import de.pxav.blocklog.model.ItemDirection;
import de.pxav.blocklog.model.SerialBlockLocation;
import de.pxav.blocklog.model.SessionItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class BlockLog extends JavaPlugin {

  private static Injector injector;

  @Override
  public void onEnable() {
    SimpleBinderModule simpleBinderModule = SimpleBinderModule.create(this);
    injector = Guice.createInjector(simpleBinderModule);

    CredentialsFile credentialsFile = injector.getInstance(CredentialsFile.class);
    credentialsFile.createFile("plugins//BlockLog", "databaseCredentials.yml");
    credentialsFile.loadToCache();

    BlockLogConfiguration settingsConfig = injector.getInstance(SettingsConfiguration.class);
    settingsConfig.createFileIfNotExists();
    settingsConfig.cacheConfig();

    BlockLogConfiguration messagesConfig = injector.getInstance(MessageConfiguration.class);
    messagesConfig.createFileIfNotExists();
    messagesConfig.cacheConfig();

    TableCreator tableCreator = injector.getInstance(TableCreator.class);
    tableCreator.createTables();
  }

  @Override
  public void onDisable() {
    injector.getInstance(SessionFactory.class).close();
  }

  public static Injector getInjector() {
    return injector;
  }

}
