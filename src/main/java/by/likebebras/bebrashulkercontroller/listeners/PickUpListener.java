package by.likebebras.bebrashulkercontroller.listeners;

import by.likebebras.bebrashulkercontroller.PlayerManager;
import by.likebebras.bebrashulkercontroller.utilities.Config;
import by.likebebras.bebrashulkercontroller.utilities.ItemUtils;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class PickUpListener implements Listener {

    private final Config cfg;
    private final PlayerManager manager;

    public PickUpListener(Config cfg, PlayerManager manager) {
        this.manager = manager;
        this.cfg = cfg;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent e) {
        if (manager.contains(e.getPlayer().getUniqueId())) return;

        Inventory inventory = e.getPlayer().getInventory();

        ItemStack item = e.getItem().getItemStack();

        if (!ItemUtils.isNotShulker(item)) return;
        if (inventory.firstEmpty() != -1) return;

        for (ItemStack invItem : inventory.getContents()) {
            if (ItemUtils.isNotShulker(invItem)) continue;

            BlockStateMeta meta = (BlockStateMeta) invItem.getItemMeta();
            ShulkerBox shulker = (ShulkerBox) meta.getBlockState();

            Inventory shulkerInventory = shulker.getInventory();

            if (shulkerInventory.firstEmpty() == -1) continue;

            shulkerInventory.addItem(item.clone());
            meta.setBlockState(shulker);
            invItem.setItemMeta(meta);

            e.getItem().remove();

            cfg.getSound("settings.yml", "sounds.pickUp").playTo(e.getPlayer());

            e.setCancelled(true);
            return;
        }

    }
}
