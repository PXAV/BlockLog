package de.pxav.blocklog.connect.packet.in;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class CheckPermissionPacket implements IncomingPacket {

  private UUID playerUUID;
  private String permission;

  public CheckPermissionPacket(UUID playerUUID, String permission) {
    this.playerUUID = playerUUID;
    this.permission = permission;
  }

  @Override
  public void handleIncomingPacket() {}

  @Override
  public String respond() {
//    Player player = Bukkit.getPlayer(playerUUID);
//
//    if (player == null) {
//      return "checkPermission_" + playerUUID + "_" + permission + "_false";
//    }
//
//    return "checkPermission_" + playerUUID + "_" + permission + "_" + player.hasPermission("blocklog.web." + permission);
    return "checkPermission_" + playerUUID + "_" + permission + "_true";
  }

}
