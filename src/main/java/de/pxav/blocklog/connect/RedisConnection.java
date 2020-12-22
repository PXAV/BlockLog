package de.pxav.blocklog.connect;

import de.pxav.blocklog.config.RedisAuthenticationConfig;
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
  private Jedis subscriptionClient;
  private Jedis responseClient;

  @Inject
  public RedisConnection(RedisAuthenticationConfig authenticationConfig) {
    this.authenticationConfig = authenticationConfig;
  }

  public void connect() {
    System.out.println("connecting...");
    String host = authenticationConfig.getString("host");
    int port = authenticationConfig.getInteger("port");
    boolean ssl = authenticationConfig.getBoolean("use-ssl");
    System.out.println("query connection data from config");

    this.subscriptionClient = new Jedis(host, port, ssl);
    this.responseClient = new Jedis(host, port, ssl);

    if (authenticationConfig.getBoolean("use-password")) {
      String password = authenticationConfig.getString("password");

      if (authenticationConfig.getBoolean("use-username")) {
        String username = authenticationConfig.getString("username");
        this.subscriptionClient.auth(username, password);
        this.responseClient.auth(username, password);
        return;
      }

      this.subscriptionClient.auth(password);
      this.responseClient.auth(password);
    }
  }

  public void disconnect() {
    this.subscriptionClient.disconnect();
    this.responseClient.disconnect();
  }

  public Jedis getSubscriptionClient() {
    return subscriptionClient;
  }

  public Jedis getResponseClient() {
    return responseClient;
  }

}
