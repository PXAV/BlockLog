package de.pxav.blocklog;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.blocklog.database.CredentialsFile;
import de.pxav.blocklog.inject.SimpleBinderModule;
import org.bukkit.plugin.java.JavaPlugin;

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
    credentialsFile.writeDefaults();
    credentialsFile.loadToCache();

  }

  @Override
  public void onDisable() {

  }

  public static Injector getInjector() {
    return injector;
  }

}
