package by.likebebras.bebrashulkercontroller.utilities;

import by.likebebras.bebralib.ez.EzConfig;
import by.likebebras.bebralib.ez.EzPlugin;
import org.bukkit.event.inventory.ClickType;

public class Config extends EzConfig {

    public boolean useInteract, useInvClick, usePickUp, needShift;
    public ClickType clickType;

    public Config(EzPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load(){
        load("settings.yml", "messages.yml");

        useInvClick = getBoolean("settings.yml", "InvClickOpen.enabled", true);
        useInteract = getBoolean("settings.yml", "AirClickOpen.enabled", true);
        usePickUp = getBoolean("settings.yml", "PickUpItems.enabled", true);

        needShift = getBoolean("settings.yml", "AirClickOpen.needShift", true);

        clickType = ClickType.valueOf(getStringFrom("settings.yml", "InvClickOpen.click", "SHIFT_RIGHT").toUpperCase());
    }
}