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

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_inventory_sessions (" +
              "id INT PRIMARY KEY," +
              "playerUUID VARCHAR(50)," +
              "playerName VARCHAR(20)," +
              "sessionStart TIMESTAMP," +
              "sessionEnd TIMESTAMP," +
              "blockLocation VARCHAR(30)," +
              "inventoryType VARCHAR(30)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_session_items (" +
              "id INT PRIMARY KEY," +
              "session_id INT," +
              "material VARCHAR(20)," +
              "amount INT," +
              "itemDirection VARCHAR(15)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_configuration (" +
              "id INT PRIMARY KEY," +
              "config_key VARCHAR(100)," +
              "config_value VARCHAR(255)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_blacklisted_blocks (" +
              "id INT PRIMARY KEY," +
              "material VARCHAR(30)," +
              "since TIMESTAMP," +
              "allowBreaking VARCHAR(5)," +
              "allowPlacing VARCHAR(5)," +
              "allowInteracting VARCHAR(5)" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_item_frame_interactions (" +
              "id INT PRIMARY KEY," +
              "playerUUID VARCHAR(50)," +
              "playerName VARCHAR(20)," +
              "blockLocation VARCHAR(30)," +
              "material VARCHAR(30)," +
              "time TIMESTAMP" +
              ")")
              .executeUpdate();

      session.createSQLQuery("CREATE TABLE IF NOT EXISTS bl_player_block_updates (" +
              "id INT PRIMARY KEY," +
              "playerUUID VARCHAR(50)," +
              "fromMaterial VARCHAR(30)," +
              "toMaterial VARCHAR(30)," +
              "serialTime TIMESTAMP," +
              "location VARCHAR(30)" +
              ")")
              .executeUpdate();


      transaction.commit();
    }
  }

}
