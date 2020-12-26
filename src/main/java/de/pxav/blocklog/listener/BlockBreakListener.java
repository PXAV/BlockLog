package de.pxav.blocklog.listener;

import de.pxav.blocklog.database.repo.BlacklistedBlockRepository;
import de.pxav.blocklog.database.repo.PlayerBlockUpdateRepository;
import de.pxav.blocklog.model.BlacklistedBlock;
import de.pxav.blocklog.model.BlockUpdateType;
import de.pxav.blocklog.model.PlayerBlockUpdate;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import de.pxav.blocklog.thread.AsyncThreadExecutor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class BlockBreakListener implements Listener {

  private BlacklistedBlockRepository blockRepository;
  private PlayerBlockUpdateRepository blockUpdateRepository;

  @Inject
  public BlockBreakListener(BlacklistedBlockRepository blockRepository, PlayerBlockUpdateRepository blockUpdateRepository) {
    this.blockRepository = blockRepository;
    this.blockUpdateRepository = blockUpdateRepository;
  }

  @EventHandler
  public void handleBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    BlacklistedBlock blacklistedBlock = blockRepository.getByMaterial(event.getBlock().getType());

    if (blacklistedBlock == null || blacklistedBlock.breakingAllowed()) {
      return;
    }

    event.setCancelled(true);

    Material material = event.getBlock().getLocation().getWorld().getBlockAt(event.getBlock().getLocation()).getType();
    PlayerBlockUpdate blockUpdate = new PlayerBlockUpdate(
            player.getUniqueId(),
            material,
            material,
            LocalDateTime.now(),
            new SerialBlockLocation(event.getBlock().getLocation()),
            BlockUpdateType.BLOCKED
    );
    blockUpdateRepository.save(blockUpdate);

  }

}
