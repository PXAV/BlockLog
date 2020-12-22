package de.pxav.blocklog.inject;

import com.google.inject.AbstractModule;
import de.pxav.blocklog.database.SessionFactoryProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.hibernate.SessionFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleBinderModule extends AbstractModule {

  private JavaPlugin mainClass;

  private SimpleBinderModule(JavaPlugin mainClass) {
    this.mainClass = mainClass;
  }

  public static SimpleBinderModule create(JavaPlugin mainClass) {
    return new SimpleBinderModule(mainClass);
  }

  @Override
  protected void configure() {
    bind(JavaPlugin.class).toInstance(this.mainClass);
    bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
    bind(SessionFactory.class).toProvider(SessionFactoryProvider.class);
  }

}
