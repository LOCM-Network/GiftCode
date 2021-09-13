package me.phuongaz.giftcode.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.phuongaz.giftcode.form.FormStorage;

public class GiftCodeCommand extends Command{

    public GiftCodeCommand(){
        super("giftcode", "GiftCode command");
    }
    
    public boolean execute(CommandSender sender, String label, String[] args){
        FormStorage.playerForm((Player) sender);
        return true;
    }
}
