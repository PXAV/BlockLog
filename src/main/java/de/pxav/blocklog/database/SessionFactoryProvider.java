package de.pxav.blocklog.database;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.blocklog.BlockLog;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
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

  private final CredentialsFile credentialsFile;
  private SessionFactory sessionFactory;

  @Inject
  public SessionFactoryProvider(CredentialsFile credentialsFile) {
    this.credentialsFile = credentialsFile;
  }

  public SessionFactory get() {
    if (sessionFactory != null) {
      return sessionFactory;
    }

//    Properties prop= new Properties();
//
//          String url = "jdbc:mysql://"
//                      + this.credentialsFile.getHost()
//                      + ":"
//                      + this.credentialsFile.getPort()
//                      + "/"
//                      + this.credentialsFile.getDatabase();
//    System.out.println("USING URL " + url);
//    System.out.println("USING CREDENTIALS " + credentialsFile.getUsername() + " pwd=" + credentialsFile.getPassword());
//
//    prop.setProperty("hibernate.connection.url", url);
//    prop.setProperty("hibernate.connection.username", this.credentialsFile.getUsername());
//    prop.setProperty("hibernate.connection.password", this.credentialsFile.getPassword());
//    prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//    prop.setProperty(Environment.SHOW_SQL, "false");
//    prop.setProperty(Environment.USE_SECOND_LEVEL_CACHE, "true");
//    prop.setProperty(Environment.USE_QUERY_CACHE, "true");
//    prop.setProperty(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
//    prop.setProperty(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
//    prop.setProperty(Environment.ORDER_INSERTS, "true");
//    prop.setProperty(Environment.ORDER_UPDATES, "true");
//
//
//    return sessionFactory = new Configuration().addProperties(prop).buildSessionFactory();


    Thread.currentThread().setContextClassLoader(BlockLog.class.getClassLoader());

    try {
      String url = "jdbc:mysql://"
                      + this.credentialsFile.getHost()
                      + ":"
                      + this.credentialsFile.getPort()
                      + "/"
                      + this.credentialsFile.getDatabase();

      StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

      Map<String, Object> settings = Maps.newHashMap();
      settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
      settings.put(Environment.URL, url);
      settings.put(Environment.USER, this.credentialsFile.getUsername());
      settings.put(Environment.PASS, this.credentialsFile.getPassword());
      // settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
//      settings.put(Environment.HBM2DDL_AUTO, "update");
//      settings.put(Environment.SHOW_SQL, false);
//      settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//      settings.put("hibernate.hikari.connectionTimeout", "20000");
//      settings.put("hibernate.hikari.minimumIdle", "10");
//      settings.put("hibernate.hikari.idleTimeout", "300000");
      settings.put(Environment.USE_SECOND_LEVEL_CACHE, "true");
//      settings.put(Environment.USE_QUERY_CACHE, "true");
      settings.put(Environment.CACHE_REGION_FACTORY, "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
//      settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
//      settings.put(Environment.ORDER_INSERTS, true);
//      settings.put(Environment.ORDER_UPDATES, true);

      //Logger.getLogger("org.hibernate").setLevel(Level.OFF);
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
