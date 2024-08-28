package by.likebebras.bebrashulkercontroller;

import by.likebebras.bebralib.ez.EzPlugin;
import by.likebebras.bebralib.ez.cmd.EzCommand;

public class ShulkerCommand extends EzCommand {

    private final EzPlugin daddy;

    public ShulkerCommand(EzPlugin daddy) {
        super("shulkers");
        this.daddy = daddy;

        onCommand((cs, l, args) ->{
            daddy.reloadConfig();
            cs.sendMessage(daddy.getCfg().getStringFrom("messages.yml", "reloaded"));
        });
    }
}
