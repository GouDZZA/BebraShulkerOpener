package by.likebebras.bebrashulkercontroller.listeners;

import by.likebebras.bebrashulkercontroller.PlayerManager;
import by.likebebras.bebrashulkercontroller.utilities.Config;
import by.likebebras.bebrashulkercontroller.utilities.ShulkerMenu;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InventoryListener implements Listener {

    private final Config cfg;
    private final PlayerManager playerManager;

    public InventoryListener(Config cfg, PlayerManager manager) {
        this.cfg = cfg;
        this.playerManager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!cfg.useInvClick) return;
        if (!(e.getClickedInventory() instanceof PlayerInventory)) return;

        @NotNull Player issuer = (Player) e.getWhoClicked();

        if (playerManager.contains(issuer.getUniqueId())) {
            if (isShulker(e.getCurrentItem()) || isShulker(e.getCursor())){
                e.setCancelled(true);
            }
        }

        if (e.getClick() == cfg.clickType) {
            if (isShulker(e.getCurrentItem())){
                ShulkerMenu menu = new ShulkerMenu(e.getCurrentItem());

                if (playerManager.containsKeyVal(issuer.getUniqueId(), menu)) return;

                e.setCancelled(true);

                menu.showTo(issuer);

                cfg.getSound("settings.yml", "sounds.open").playTo(issuer);

                playerManager.put(issuer.getUniqueId(), menu);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (playerManager.containsKeyItem(e.getPlayer().getUniqueId(), e.getItemInHand())) e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        UUID id = e.getPlayer().getUniqueId();
        if (playerManager.contains(e.getPlayer().getUniqueId())){

            playerManager.get(id).close();

            cfg.getSound("settings.yml", "sounds.close").playTo((Player) e.getPlayer());

            playerManager.remove(id);
        }
    }

    private boolean isValid(ItemStack item){
        return item != null && item.getAmount() > 0 && item.getType() != Material.AIR && item.getItemMeta() != null;
    }

    private boolean isShulker(ItemStack item) {
        if (!isValid(item)) return false;

        if (!(item.getItemMeta() instanceof BlockStateMeta meta)) return false;

        return (meta.getBlockState() instanceof ShulkerBox);
    }
}
