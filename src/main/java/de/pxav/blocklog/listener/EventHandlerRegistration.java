package de.pxav.blocklog.listener;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.classgraph.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class EventHandlerRegistration {

  private Injector injector;
  private JavaPlugin javaPlugin;

  @Inject
  public EventHandlerRegistration(Injector injector, JavaPlugin javaPlugin) {
    this.injector = injector;
    this.javaPlugin = javaPlugin;
  }

  public void registerAllListeners(String... packageNames) {
    Preconditions.checkNotNull(packageNames);

    try (ScanResult scanResult = new ClassGraph().whitelistPackages(packageNames).enableAllInfo().scan()) {
      ClassInfoList classInfos = scanResult.getClassesImplementing(Listener.class.getName());
      for (ClassInfo classInfo : classInfos) {
        for (MethodInfo methodInfo : classInfo.getMethodInfo()) {
          if (!methodInfo.hasAnnotation(EventHandler.class.getName())) {
            continue;
          }

          Method method = methodInfo.loadClassAndGetMethod();
          EventHandler handler = method.getAnnotation(EventHandler.class);

          Bukkit.getPluginManager()
                  .registerEvent(
                          ((Class<? extends Event>) method.getParameterTypes()[0]),
                          new Listener() {},
                          handler.priority(),
                          (listener, event) -> {
                            try {
                              if (method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                                method.invoke(injector.getInstance(method.getDeclaringClass()), event);
                              }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                              e.printStackTrace();
                            }
                          },
                          javaPlugin,
                          handler.ignoreCancelled());
        }
      }
    }
  }

}
