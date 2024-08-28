package by.likebebras.bebrashulkercontroller;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

@Getter
public class ShulkerMenu implements InventoryHolder {
    private final Inventory inv;
    private final ItemStack item;

    public ShulkerMenu(ItemStack shulker){
        this.inv = getShulkerInv(shulker);
        this.item = shulker;
    }

    public void showTo(Player p){
        p.closeInventory();
        p.openInventory(inv);
    }

    public boolean isSame(ItemStack item){
        return this.item.isSimilar(item);
    }

    public void close(){
        if (isNotShulker(item)) return;

        BlockStateMeta meta = (BlockStateMeta)item.getItemMeta();

        ShulkerBox box = getShulkerBox(item);

        if (box == null) return;
        box.getInventory().setContents(this.inv.getContents());

        meta.setBlockState(box);

        item.setItemMeta(meta);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    private Inventory getShulkerInv(ItemStack item){
        if (isNotShulker(item)) return null;

        ShulkerBox shulker = (ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState();

        return shulker.getInventory();
    }

    private ShulkerBox getShulkerBox(ItemStack item){
        if (isNotShulker(item)) return null;

        return (ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState();
    }

    private boolean isValid(ItemStack item){
        return item != null && item.getAmount() > 0 && item.getType() != Material.AIR && item.getItemMeta() != null;
    }

    private boolean isNotShulker(ItemStack item) {
        if (!isValid(item)) return true;

        if (!(item.getItemMeta() instanceof BlockStateMeta meta)) return true;

        return (!(meta.getBlockState() instanceof ShulkerBox));
    }
}
