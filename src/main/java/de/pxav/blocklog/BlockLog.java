package de.pxav.blocklog;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.blocklog.config.*;
import de.pxav.blocklog.connect.RedisConnection;
import de.pxav.blocklog.connect.RedisPubSub;
import de.pxav.blocklog.database.TableCreator;
import de.pxav.blocklog.database.repo.BlacklistedBlockRepository;
import de.pxav.blocklog.database.repo.InventorySessionRepository;
import de.pxav.blocklog.database.repo.ItemFrameInteractionRepository;
import de.pxav.blocklog.inject.SimpleBinderModule;
import de.pxav.blocklog.model.*;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import de.pxav.blocklog.model.serial.SerialTime;
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

//    Bukkit.getScheduler().runTaskLater(this, () -> {
//      // WITHOUT DASHES!!!!!!!!!!
//      injector.getInstance(RedisPubSub.class).publish("ask_web", "createAccount_" + UUID.randomUUID().toString().replace("-", "") + "_" + "TestUser" + "_passwordHash");
//    }, 80L);

    Bukkit.getScheduler().runTaskLater(this, () -> {
      System.out.println("Scheduler expired! Creating dummy objects...");
      SerialBlockLocation blockLocation = new SerialBlockLocation("world", 12, 70, -34);
      System.out.println("created serial block location");
      SerialTime sessionStart = new SerialTime(6, 15, 23, 22, 12, 2020);
      SerialTime sessionEnd = new SerialTime(23, 16, 23, 22, 12, 2020);
      InventorySession inventorySession = new InventorySession(UUID.randomUUID(), "test", sessionStart, sessionEnd, blockLocation, InventoryType.CHEST);
      SessionItem sessionItem = new SessionItem(inventorySession, Material.GOLDEN_APPLE, 3, ItemDirection.TO_INVENTORY);
      inventorySession.addItem(sessionItem);
      System.out.println("created inventory session object");
      injector.getInstance(InventorySessionRepository.class).save(inventorySession);

      BlacklistedBlock tntBlock = new BlacklistedBlock(Material.TNT, sessionStart);
      injector.getInstance(BlacklistedBlockRepository.class).save(tntBlock);

      ItemFrameInteraction itemFrameInteraction = new ItemFrameInteraction(UUID.randomUUID(), "TestName", blockLocation, Material.DIAMOND_SWORD, sessionEnd);
      injector.getInstance(ItemFrameInteractionRepository.class).save(itemFrameInteraction);

      System.out.println("saved to repo");
    }, 20L);

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
