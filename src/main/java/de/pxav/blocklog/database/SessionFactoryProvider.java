package de.pxav.blocklog.database;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.blocklog.BlockLog;
import de.pxav.blocklog.config.SettingsConfiguration;
import de.pxav.blocklog.config.SqlDatabaseCredentialsConfig;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SessionFactoryProvider implements Provider<SessionFactory> {

  private static StandardServiceRegistry registry;

  private final SqlDatabaseCredentialsConfig sqlConfig;
  private final SettingsConfiguration settingsConfig;
  private SessionFactory sessionFactory;

  @Inject
  public SessionFactoryProvider(SqlDatabaseCredentialsConfig sqlConfig, SettingsConfiguration settingsConfig) {
    this.sqlConfig = sqlConfig;
    this.settingsConfig = settingsConfig;
  }

  public SessionFactory get() {
    if (sessionFactory != null) {
      return sessionFactory;
    }

    if (!settingsConfig.getBoolean("database.hibernate.show_log")) {
      Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    Thread.currentThread().setContextClassLoader(BlockLog.class.getClassLoader());

    try {

      String databaseType = settingsConfig.getString("database.database_type");
      StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
      Map<String, Object> settings = Maps.newHashMap();

      if (databaseType.equalsIgnoreCase("MY_SQL")) {
        String url = "jdbc:mysql://"
                + this.sqlConfig.getString("host")
                + ":"
                + this.sqlConfig.getInteger("port")
                + "/"
                + this.sqlConfig.getString("database");

        settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        settings.put(Environment.URL, url);
        settings.put(Environment.USER, this.sqlConfig.getString("username"));
        settings.put(Environment.PASS, this.sqlConfig.getString("password"));
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
      } else if (databaseType.equalsIgnoreCase("SQLITE")) {
        String url = "jdbc:sqlite:" + settingsConfig.getString("database.sqlite.database_name");

        settings.put(Environment.DRIVER, "org.sqlite.JDBC");
        settings.put(Environment.URL, url);
        settings.put(Environment.DIALECT, "org.sqlite.hibernate.dialect.SQLiteDialect");
      } else {
        System.out.println("Configuration error: Unknown database type '" + databaseType + "'");
      }

      settings.put(Environment.SHOW_SQL, settingsConfig.getBoolean("database.hibernate.show_sql"));
      settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
      settings.put(Environment.USE_QUERY_CACHE, "true");
      settings.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
      settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true"); // enable automatic transaction for lazy loading
      settings.put(Environment.ORDER_INSERTS, true);
      settings.put(Environment.ORDER_UPDATES, true);
      settings.put(Environment.USE_SQL_COMMENTS, settingsConfig.getBoolean("database.hibernate.use_sql_comments"));

      registryBuilder.applySettings(settings);
      registry = registryBuilder.build();

      Collection<Class<?>> entities = Lists.newArrayList();
      try (ScanResult scanResult = new ClassGraph().enableAllInfo().whitelistPackages("de.pxav.blocklog").scan()) {
        ClassInfoList classInfos = scanResult.getClassesWithAnnotation(Entity.class.getName());
        classInfos.forEach(current -> {
          entities.add(current.loadClass());
        });
      }

      MetadataSources sources = new MetadataSources(registry);
      entities.forEach(sources::addAnnotatedClass);
      Metadata metadata = sources.getMetadataBuilder().build();

      return sessionFactory = metadata.getSessionFactoryBuilder().build();

    } catch (Exception e) {
      if (SessionFactoryProvider.registry != null) {
        StandardServiceRegistryBuilder.destroy(SessionFactoryProvider.registry);
      }
      e.printStackTrace();
    }
    return null;
  }

}
