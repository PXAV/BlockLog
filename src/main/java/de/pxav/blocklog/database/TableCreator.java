package de.pxav.blocklog.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

  public void createTables() {
    try (Session session = this.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS inventory_sessions (" +
              "id INT PRIMARY KEY," +
              "playerUUID VARCHAR(50)," +
              "playerName VARCHAR(20)," +
              "blockLocation VARCHAR(30)," +
              "inventoryType VARCHAR(30)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS session_items (" +
              "id INT PRIMARY KEY," +
              "session_id INT," +
              "material VARCHAR(20)," +
              "amount INT," +
              "itemDirection VARCHAR(15)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS session_items (" +
              "id INT PRIMARY KEY," +
              "config_key VARCHAR(100)," +
              "config_value VARCHAR(255)" +
              ")")
              .executeUpdate();

      transaction.commit();
    }
  }

}
