package de.pxav.blocklog.connect;

import de.pxav.blocklog.connect.packet.in.GetBlocksPacket;
import de.pxav.blocklog.connect.packet.in.IncomingPacket;
import de.pxav.blocklog.connect.packet.in.CheckPermissionPacket;
import org.bukkit.Material;
import redis.clients.jedis.JedisPubSub;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class RedisPubSub {

  private RedisConnection redisConnection;
  private ExecutorService executorService;

  @Inject
  public RedisPubSub(RedisConnection redisConnection, ExecutorService executorService) {
    this.redisConnection = redisConnection;
    this.executorService = executorService;
  }

  public void listen() {
    executorService.execute(() -> {
      try {
        JedisPubSub pubSub = new JedisPubSub() {
          @Override
          public void onMessage(String channel, String message) {
            if (channel.equalsIgnoreCase("ask_pl")) {

              String[] generalArgs = message.split("_");
              System.out.println("Received " + message);

              if (message.startsWith("checkPermission")) {
                String[] args = Arrays.copyOfRange(generalArgs, 1, generalArgs.length);
                for (String arg : args) {
                  System.out.println("ARG " + arg);
                }
                IncomingPacket packet = new CheckPermissionPacket(UUID.fromString(args[0]), args[1]);
                packet.handleIncomingPacket();
                String response = packet.respond();
                if (response != null) {
                  publish("ask_pl_resp", response);
                }
              }

              if (message.startsWith("getBlocks")) {
                IncomingPacket packet = new GetBlocksPacket();
                packet.handleIncomingPacket();
                String response = packet.respond();
                if (response != null) {
                  publish("ask_pl_resp", response);
                }
              }

              return;
            }

            if (channel.equalsIgnoreCase("ask_web_resp")) {

              if (message.startsWith("createAccount")) {
                System.out.println("received web response: " + message);
              }

            }
          }
        };
        redisConnection.getSubscriptionClient().subscribe(pubSub, "ask_pl", "ask_web_resp");
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  // receive updateConfig from web to update config file

  public void publish(String channel, String message) {
    this.redisConnection.getResponseClient().publish(channel, message);
  }

}
