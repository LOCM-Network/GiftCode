package me.phuongaz.giftcode.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import me.phuongaz.giftcode.form.FormStorage;

public class AdminCommand extends Command{

    public AdminCommand(){
        super("admingc", "GiftCode admin command");
    }
    
    public boolean execute(CommandSender sender, String label, String[] args){
        if(sender.hasPermission("giftcode.admin") && sender instanceof Player){
            FormStorage.sendMainForm((Player) sender);
        }
        return true;
    }

}
