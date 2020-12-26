package de.pxav.blocklog.listener;

import com.google.inject.Inject;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.time.LocalDateTime;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class PlayerDestroyPhysicalEvent implements Listener {

  private BlacklistedBlockRepository blacklistedBlockRepository;
  private PlayerBlockUpdateRepository blockUpdateRepository;

  @Inject
  public PlayerDestroyPhysicalEvent(BlacklistedBlockRepository blacklistedBlockRepository, PlayerBlockUpdateRepository blockUpdateRepository) {
    this.blacklistedBlockRepository = blacklistedBlockRepository;
    this.blockUpdateRepository = blockUpdateRepository;
  }

  @EventHandler
  public void handlePlayerPhysicalDestruction(PlayerInteractEvent event) {
    if (event.getAction() != Action.PHYSICAL || event.getClickedBlock() == null) {
      return;
    }

    Player player = event.getPlayer();
    BlacklistedBlock blacklistedBlock = blacklistedBlockRepository.getByMaterial(event.getClickedBlock().getType());

    PlayerBlockUpdate blockUpdate = new PlayerBlockUpdate(
            player.getUniqueId(),
            event.getClickedBlock().getType(),
            Material.AIR,
            LocalDateTime.now(),
            new SerialBlockLocation(event.getClickedBlock().getLocation()),
            BlockUpdateType.REPLACE
    );

    if (blacklistedBlock == null || blacklistedBlock.interactingAllowed()) {
      if (event.getClickedBlock().getType() == Material.FARMLAND && !event.isCancelled()) {
        blockUpdate.setToMaterial(Material.DIRT);
        blockUpdateRepository.save(blockUpdate);
      }
      return;
    }

    event.setCancelled(true);
    blockUpdate.setBlockUpdateType(BlockUpdateType.BLOCKED_BREAK);
    blockUpdateRepository.save(blockUpdate);

  }

}
