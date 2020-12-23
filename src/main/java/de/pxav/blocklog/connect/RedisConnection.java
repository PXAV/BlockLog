package de.pxav.blocklog.connect;

import de.pxav.blocklog.config.RedisAuthenticationConfig;
import de.pxav.blocklog.config.SettingsConfiguration;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class RedisConnection {

  private RedisAuthenticationConfig authenticationConfig;
  private SettingsConfiguration settingsConfiguration;
  private Jedis subscriptionClient;
  private Jedis responseClient;

  @Inject
  public RedisConnection(RedisAuthenticationConfig authenticationConfig, SettingsConfiguration settingsConfiguration) {
    this.authenticationConfig = authenticationConfig;
    this.settingsConfiguration = settingsConfiguration;
  }

  public boolean connect() {
    if (!settingsConfiguration.getBoolean("enable_redis_communication")) {
      System.out.println("Redis communication is disabled in configuration. Skipping connection.");
      return false;
    }

    String host = authenticationConfig.getString("host");
    int port = authenticationConfig.getInteger("port");
    boolean ssl = authenticationConfig.getBoolean("use-ssl");

    this.subscriptionClient = new Jedis(host, port, ssl);
    this.responseClient = new Jedis(host, port, ssl);

    if (authenticationConfig.getBoolean("use-password")) {
      String password = authenticationConfig.getString("password");

      if (authenticationConfig.getBoolean("use-username")) {
        String username = authenticationConfig.getString("username");
        this.subscriptionClient.auth(username, password);
        this.responseClient.auth(username, password);
        return true;
      }

      this.subscriptionClient.auth(password);
      this.responseClient.auth(password);
    }
    return true;
  }

  public void disconnect() {
    if (this.subscriptionClient.isConnected()) {
      this.subscriptionClient.disconnect();
    }

    if (this.responseClient.isConnected()) {
      this.responseClient.disconnect();
    }
  }

  public Jedis getSubscriptionClient() {
    return subscriptionClient;
  }

  public Jedis getResponseClient() {
    return responseClient;
  }

}
