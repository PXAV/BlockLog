package de.pxav.blocklog.connect.packet.in;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface IncomingPacket {

  void handleIncomingPacket();

  String respond();

}
