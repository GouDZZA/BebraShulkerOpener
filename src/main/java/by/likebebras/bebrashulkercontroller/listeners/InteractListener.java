package by.likebebras.bebrashulkercontroller.listeners;

import by.likebebras.bebrashulkercontroller.PlayerManager;
import by.likebebras.bebrashulkercontroller.utilities.Config;
import by.likebebras.bebrashulkercontroller.utilities.ItemUtils;
import by.likebebras.bebrashulkercontroller.utilities.ShulkerMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class InteractListener implements Listener {

    private final Config cfg;
    private final PlayerManager playerManager;

    public InteractListener(Config cfg, PlayerManager manager) {
        this.cfg = cfg;
        this.playerManager = manager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (!cfg.useInteract) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (ItemUtils.isNotShulker(e.getItem())) return;
        if (cfg.needShift && !e.getPlayer().isSneaking()) return;

        Player p = e.getPlayer();
        ShulkerMenu menu = new ShulkerMenu(e.getItem());

        cfg.getSound("settings.yml", "sounds.open").playTo(p);

        menu.showTo(p);
        playerManager.put(p.getUniqueId(), menu);
    }
}
