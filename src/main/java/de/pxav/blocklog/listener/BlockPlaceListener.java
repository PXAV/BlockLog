package de.pxav.blocklog.listener;

import de.pxav.blocklog.database.repo.BlacklistedBlockRepository;
import de.pxav.blocklog.database.repo.PlayerBlockUpdateRepository;
import de.pxav.blocklog.model.BlacklistedBlock;
import de.pxav.blocklog.model.BlockUpdateType;
import de.pxav.blocklog.model.PlayerBlockUpdate;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class BlockPlaceListener implements Listener {

  private BlacklistedBlockRepository blockRepository;
  private PlayerBlockUpdateRepository blockUpdateRepository;

  @Inject
  public BlockPlaceListener(BlacklistedBlockRepository blockRepository, PlayerBlockUpdateRepository blockUpdateRepository) {
    this.blockRepository = blockRepository;
    this.blockUpdateRepository = blockUpdateRepository;
  }

  @EventHandler
  public void handleBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    BlacklistedBlock blacklistedBlock = blockRepository.getByMaterial(event.getBlock().getType());

    PlayerBlockUpdate blockUpdate = new PlayerBlockUpdate(
            player.getUniqueId(),
            event.getBlockReplacedState().getBlock().getType(),
            event.getBlock().getType(),
            LocalDateTime.now(),
            new SerialBlockLocation(event.getBlock().getLocation()),
            BlockUpdateType.PLACE
    );

    if ((blacklistedBlock != null && !blacklistedBlock.placingAllowed()) || event.isCancelled()) {
      event.setCancelled(true);
      blockUpdate.setToMaterial(event.getBlockReplacedState().getBlock().getType());
      blockUpdate.setBlockUpdateType(BlockUpdateType.BLOCKED);
    }

    blockUpdateRepository.save(blockUpdate);

  }

}
