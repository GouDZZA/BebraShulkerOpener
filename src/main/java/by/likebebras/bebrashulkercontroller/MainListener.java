package by.likebebras.bebrashulkercontroller;

import by.likebebras.bebralib.ez.EzConfig;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainListener implements Listener {

    private final HashMap<UUID, ShulkerMenu> players = new HashMap<>();
    private final EzConfig cfg;

    public MainListener(EzConfig cfg) {
        this.cfg = cfg;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!(e.getClickedInventory() instanceof PlayerInventory)) return;

        @NotNull Player issuer = (Player) e.getWhoClicked();

        if (players.containsKey(issuer.getUniqueId())) {
            if (isShulker(e.getCurrentItem()) || isShulker(e.getCursor())){
                e.setCancelled(true);
            }
        }

        if (e.getClick() == ClickType.SHIFT_RIGHT) {
            if (isShulker(e.getCurrentItem())){
                ShulkerMenu menu = new ShulkerMenu(e.getCurrentItem());

                if (containsKeyVal(issuer.getUniqueId(), menu)) return;

                e.setCancelled(true);

                menu.showTo(issuer);

                cfg.getSound("settings.yml", "sounds.open").playTo(issuer);

                players.put(issuer.getUniqueId(), menu);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if (containsKeyItem(e.getPlayer().getUniqueId(), e.getItemInHand())) e.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        UUID id = e.getPlayer().getUniqueId();
        if (players.containsKey(e.getPlayer().getUniqueId())){

            players.get(id).close();

            cfg.getSound("settings.yml", "sounds.close").playTo((Player) e.getPlayer());

            players.remove(id);
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

    private boolean containsKeyVal(UUID plr, ShulkerMenu menu){
        return players.containsKey(plr) && players.get(plr).isSame(menu.getItem());
    }

    private boolean containsKeyItem(UUID plr, ItemStack itemStack){
        return players.containsKey(plr) && players.get(plr).isSame(itemStack);
    }
}
