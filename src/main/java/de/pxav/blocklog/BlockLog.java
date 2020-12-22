package de.pxav.blocklog;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.blocklog.config.*;
import de.pxav.blocklog.connect.RedisConnection;
import de.pxav.blocklog.connect.RedisPubSub;
import de.pxav.blocklog.database.TableCreator;
import de.pxav.blocklog.inject.SimpleBinderModule;
import org.bukkit.Bukkit;
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

    BlockLogConfiguration settingsConfig = injector.getInstance(SettingsConfiguration.class);
    settingsConfig.createFileIfNotExists();
    settingsConfig.cacheConfig();

    BlockLogConfiguration messagesConfig = injector.getInstance(MessageConfiguration.class);
    messagesConfig.createFileIfNotExists();
    messagesConfig.cacheConfig();

    BlockLogConfiguration redisConfig = injector.getInstance(RedisAuthenticationConfig.class);
    redisConfig.createFileIfNotExists();
    redisConfig.cacheConfig();

    BlockLogConfiguration sqlConfig = injector.getInstance(SqlDatabaseCredentialsConfig.class);
    sqlConfig.createFileIfNotExists();
    sqlConfig.cacheConfig();

    TableCreator tableCreator = injector.getInstance(TableCreator.class);
    tableCreator.createTables();

    injector.getInstance(RedisConnection.class).connect();
    injector.getInstance(RedisPubSub.class).listen();

    Bukkit.getScheduler().runTaskLater(this, () -> {
      // WITHOUT DASHES!!!!!!!!!!
      injector.getInstance(RedisPubSub.class).publish("ask_web", "createAccount_" + UUID.randomUUID().toString().replace("-", "") + "_" + "TestUser" + "_passwordHash");
    }, 80L);

  }

  @Override
  public void onDisable() {
    injector.getInstance(RedisConnection.class).disconnect();
    injector.getInstance(SessionFactory.class).close();
  }

  public static Injector getInjector() {
    return injector;
  }

}
