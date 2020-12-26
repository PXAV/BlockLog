package de.pxav.blocklog.thread;

import javax.inject.Singleton;
import java.util.concurrent.Executors;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class AsyncThreadExecutor {

  public static void executeOnNewThread(Runnable runnable) {
    Executors.newCachedThreadPool().execute(runnable);
  }

}
