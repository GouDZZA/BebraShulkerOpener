package by.likebebras.bebrashulkercontroller.utilities;

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
        this.inv = ItemUtils.getShulkerInv(shulker);
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
        if (ItemUtils.isNotShulker(item)) return;

        BlockStateMeta meta = (BlockStateMeta)item.getItemMeta();

        ShulkerBox box = ItemUtils.getShulkerBox(item);

        if (box == null) return;
        box.getInventory().setContents(this.inv.getContents());

        meta.setBlockState(box);

        item.setItemMeta(meta);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
