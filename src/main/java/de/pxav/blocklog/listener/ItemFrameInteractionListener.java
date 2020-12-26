package de.pxav.blocklog.listener;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import de.pxav.blocklog.database.repo.BlacklistedBlockRepository;
import de.pxav.blocklog.database.repo.ItemFrameInteractionRepository;
import de.pxav.blocklog.database.repo.PlayerBlockUpdateRepository;
import de.pxav.blocklog.model.*;
import de.pxav.blocklog.model.serial.SerialBlockLocation;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.time.LocalDateTime;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ItemFrameInteractionListener implements Listener {

  private ItemFrameInteractionRepository itemFrameInteractionRepository;
  private BlacklistedBlockRepository blacklistedBlockRepository;
  private PlayerBlockUpdateRepository blockUpdateRepository;

  @Inject
  public ItemFrameInteractionListener(ItemFrameInteractionRepository itemFrameInteractionRepository, BlacklistedBlockRepository blacklistedBlockRepository, PlayerBlockUpdateRepository blockUpdateRepository) {
    this.itemFrameInteractionRepository = itemFrameInteractionRepository;
    this.blacklistedBlockRepository = blacklistedBlockRepository;
    this.blockUpdateRepository = blockUpdateRepository;
  }

  @EventHandler
  public void handleItemFrameDestruction(HangingBreakByEntityEvent event) {
    Preconditions.checkNotNull(event.getRemover());
    Preconditions.checkNotNull(event.getEntity());

    if (!(event.getEntity() instanceof ItemFrame)
            || !(event.getRemover() instanceof Player)) {
      return;
    }

    Player player = (Player) event.getRemover();
    BlacklistedBlock blacklistedBlock = blacklistedBlockRepository.getByMaterial(Material.ITEM_FRAME);

    PlayerBlockUpdate blockUpdate = new PlayerBlockUpdate(
            player.getUniqueId(),
            Material.ITEM_FRAME,
            Material.AIR,
            LocalDateTime.now(),
            new SerialBlockLocation(event.getEntity().getLocation()),
            BlockUpdateType.BREAK
    );

    if (blacklistedBlock != null && !blacklistedBlock.breakingAllowed()) {
      event.setCancelled(true);
      blockUpdate.setBlockUpdateType(BlockUpdateType.BLOCKED_BREAK);
    }

    blockUpdateRepository.save(blockUpdate);
  }

  @EventHandler
  public void handleItemFramePlacement(HangingPlaceEvent event) {
    Preconditions.checkNotNull(event.getPlayer());
    Preconditions.checkNotNull(event.getEntity());

    if (!(event.getEntity() instanceof ItemFrame)) {
       return;
    }

    Player player = event.getPlayer();
    BlacklistedBlock blacklistedBlock = blacklistedBlockRepository.getByMaterial(Material.ITEM_FRAME);

    PlayerBlockUpdate blockUpdate = new PlayerBlockUpdate(
            player.getUniqueId(),
            Material.AIR,
            Material.ITEM_FRAME,
            LocalDateTime.now(),
            new SerialBlockLocation(event.getEntity().getLocation()),
            BlockUpdateType.PLACE
    );

    if (blacklistedBlock != null && !blacklistedBlock.placingAllowed()) {
      event.setCancelled(true);
      blockUpdate.setBlockUpdateType(BlockUpdateType.BLOCKED_PLACE);
    }

    blockUpdateRepository.save(blockUpdate);

  }

  @EventHandler
  public void handleItemFrameRotateItem(PlayerInteractEntityEvent event) {
    Preconditions.checkNotNull(event.getPlayer());
    Preconditions.checkNotNull(event.getRightClicked());

    if (!(event.getRightClicked() instanceof ItemFrame)) {
      return;
    }

    Player player = event.getPlayer();
    BlacklistedBlock blacklistedBlock = blacklistedBlockRepository.getByMaterial(Material.ITEM_FRAME);
    SerialBlockLocation blockLocation = new SerialBlockLocation(event.getRightClicked().getLocation());
    ItemFrame itemFrame = (ItemFrame) event.getRightClicked();

    ItemFrameInteraction itemFrameInteraction = new ItemFrameInteraction(player.getUniqueId(),
            blockLocation,
            itemFrame.getItem().getType(),
            ItemDirection.ROTATE,
            LocalDateTime.now());

    if (blacklistedBlock != null && !blacklistedBlock.interactingAllowed()) {
      event.setCancelled(true);
      itemFrameInteraction.setAction(ItemDirection.BLOCKED_ROTATE);
    }

    itemFrameInteractionRepository.save(itemFrameInteraction);
  }

  @EventHandler
  public void handlePlayerItemTakeout(EntityDamageByEntityEvent event) {

    if (!(event.getEntity() instanceof ItemFrame)
            || !(event.getDamager() instanceof Player)) {
      return;
    }

    Player player = (Player) event.getDamager();
    BlacklistedBlock blacklistedBlock = blacklistedBlockRepository.getByMaterial(Material.ITEM_FRAME);
    SerialBlockLocation blockLocation = new SerialBlockLocation(event.getEntity().getLocation());
    ItemFrame itemFrame = (ItemFrame) event.getEntity();

    ItemFrameInteraction itemFrameInteraction = new ItemFrameInteraction(player.getUniqueId(),
            blockLocation,
            itemFrame.getItem().getType(),
            ItemDirection.TO_SELF,
            LocalDateTime.now());

    if (blacklistedBlock != null && !blacklistedBlock.interactingAllowed()) {
      event.setCancelled(true);
      itemFrameInteraction.setAction(ItemDirection.BLOCKED_TO_SELF);
    }

    itemFrameInteractionRepository.save(itemFrameInteraction);
  }

}
