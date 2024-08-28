package by.likebebras.bebrashulkercontroller;

import by.likebebras.bebrashulkercontroller.utilities.ShulkerMenu;
import org.bukkit.entity.Shulker;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private final HashMap<UUID, ShulkerMenu> players = new HashMap<>();

    public PlayerManager(){
    }

    public void put(UUID id, ShulkerMenu menu){
        players.put(id, menu);
    }

    public void remove(UUID id){
        players.remove(id);
    }
    public ShulkerMenu get(UUID id){
        return players.get(id);
    }

    public boolean contains(UUID id){
        return players.containsKey(id);
    }

    public boolean containsKeyVal(UUID plr, ShulkerMenu menu){
        return players.containsKey(plr) && players.get(plr).isSame(menu.getItem());
    }

    public boolean containsKeyItem(UUID plr, ItemStack itemStack){
        return players.containsKey(plr) && players.get(plr).isSame(itemStack);
    }
}
