package de.pxav.blocklog.thread;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface Retrievable {

  /**
   * Executes a calculation on the main thread and returns
   * the result as an object instance.
   *
   * @return The result of the operation
   */
  Object retrieve();

}
