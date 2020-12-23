package de.pxav.blocklog.connect.packet;

import de.pxav.blocklog.connect.RedisPubSub;
import de.pxav.blocklog.connect.packet.in.CheckPermissionPacket;
import de.pxav.blocklog.connect.packet.out.CreateAccountPacket;
import de.pxav.blocklog.connect.packet.out.OutgoingPacket;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class PacketRepository {

  private RedisPubSub redisPubSub;

  @Inject
  public PacketRepository(RedisPubSub redisPubSub) {
    this.redisPubSub = redisPubSub;
  }

  public CheckPermissionPacket newCheckPermissionPacket(UUID playerUUID, String permission) {
    return new CheckPermissionPacket(playerUUID, permission);
  }


  public void sendOutgoingPacket(OutgoingPacket packet) {
  }

}
