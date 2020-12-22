package de.pxav.blocklog.model.serial;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.Serializable;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SerialBlockLocation implements Serializable {

  private String world;
  private int x;
  private int y;
  private int z;

  public SerialBlockLocation(Location location) {
    this.world = location.getWorld().getName();
    this.x = location.getBlockX();
    this.y = location.getBlockY();
    this.z = location.getBlockZ();
  }

  public SerialBlockLocation(String world, int x, int y, int z) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public SerialBlockLocation() {}

  public String getWorld() {
    return world;
  }

  public void setWorld(String world) {
    this.world = world;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  public World getBukkitWorld() {
    return Bukkit.getWorld(this.world);
  }

  public Block getBlock() {
    return this.getBukkitWorld().getBlockAt(this.getBukkitLocation());
  }

  public Location getBukkitLocation() {
    return new Location(this.getBukkitWorld(), this.x, this.y, this.z);
  }

}
