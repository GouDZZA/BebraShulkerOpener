package by.likebebras.bebrashulkercontroller;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.managers.CommandManager;
import by.likebebras.bebrashulkercontroller.cmds.ShulkerCommand;
import by.likebebras.bebrashulkercontroller.listeners.InteractListener;
import by.likebebras.bebrashulkercontroller.listeners.InventoryListener;
import by.likebebras.bebrashulkercontroller.utilities.Config;

public final class BebraShulkerController extends EzPlugin {

    private final CommandManager manager = new CommandManager(this);
    private final PlayerManager playerManager = new PlayerManager();

    @Override
    public void onEnable() {
        cfg = new Config(this);

        cfg.load();

        registerListener(new InventoryListener((Config) cfg, playerManager));
        registerListener(new InteractListener((Config) cfg, playerManager));

        new ShulkerCommand(this).register(manager);
    }

    @Override
    public void onDisable() {
    }
}
