package by.likebebras.bebrashulkercontroller.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

@UtilityClass
public class ItemUtils {
    public Inventory getShulkerInv(ItemStack item){
        ShulkerBox shulker = getShulkerBox(item);

        return shulker == null ? null : shulker.getInventory();
    }

    public ShulkerBox getShulkerBox(ItemStack item){
        if (isNotShulker(item)) return null;

        return (ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState();
    }

    public boolean isValid(ItemStack item){
        return item != null && item.getAmount() > 0 && item.getType() != Material.AIR && item.getItemMeta() != null;
    }

    public boolean isNotShulker(ItemStack item) {
        if (!isValid(item)) return true;

        if (!(item.getItemMeta() instanceof BlockStateMeta meta)) return true;

        return (!(meta.getBlockState() instanceof ShulkerBox));
    }
}
