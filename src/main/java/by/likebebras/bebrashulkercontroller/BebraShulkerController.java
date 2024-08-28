package by.likebebras.bebrashulkercontroller;

import by.likebebras.bebralib.ez.EzConfig;
import by.likebebras.bebralib.ez.EzPlugin;

public final class BebraShulkerController extends EzPlugin {

    @Override
    public void onEnable() {
        cfg = new EzConfig(this);

        registerListener(new MainListener(cfg));
    }

    @Override
    public void onDisable() {
    }
}
