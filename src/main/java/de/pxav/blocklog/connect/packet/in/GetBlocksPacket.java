package de.pxav.blocklog.connect.packet.in;

import org.bukkit.Material;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class GetBlocksPacket implements IncomingPacket {

  @Override
  public void handleIncomingPacket() {

  }

  @Override
  public String respond() {
    StringBuilder responseBuilder = new StringBuilder();

    for (Material current : Material.values()) {
      if (!current.isBlock()) {
        continue;
      }

      responseBuilder.append(current.toString()).append(",");
    }
    responseBuilder.setLength(responseBuilder.length() - 1);

    System.out.println("responding " + responseBuilder.toString());
    return responseBuilder.toString();
  }

}
