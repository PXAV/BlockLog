package de.pxav.blocklog.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class TableCreator {

  private SessionFactory sessionFactory;

  @Inject
  public TableCreator(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void createSessionTable() {
    try (Session session = this.sessionFactory.openSession()) {
      session.createSQLQuery("CREATE TABLE IF NOT EXISTS inventory_sessions (" +
              "id INT PRIMARY KEY AUTO_INCREMENT," +
              "playerUUID VARCHAR(50)," +
              "playerName VARCHAR(20)," +
              "blockLocation VARCHAR(30)," +
              "inventoryType VARCHAR(30)" +
              ")")
              .executeUpdate();
    }
  }

  public void createItemTable() {
    try (Session session = this.sessionFactory.openSession()) {
      session.createSQLQuery("CREATE TABLE IF NOT EXISTS session_items (" +
              "id INT PRIMARY KEY AUTO_INCREMENT," +
              "session_id INT," +
              "material VARCHAR(20)," +
              "amount INT," +
              "itemDirection VARCHAR(15)" +
              ")")
              .executeUpdate();
    }
  }

}
