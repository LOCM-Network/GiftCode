package me.phuongaz.giftcode;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.TextFormat;
import me.phuongaz.giftcode.event.PlayerGiftCodeEvent;
import me.phuongaz.giftcode.provider.SQLiteProvider;

public class EventListener implements Listener{

    @EventHandler
    public void onGiftCode(PlayerGiftCodeEvent event){
        Player player = event.getPlayer();
        GiftCode giftcode = event.getGiftcode();
        for(String gift : giftcode.getRewards()){
            Server.getInstance().dispatchCommand(new ConsoleCommandSender(), gift.replace("{player}", player.getName()));
        }
        player.sendMessage(TextFormat.colorize("&l&fNhập thành công code:&e " + giftcode.getGiftcode()));
        if(giftcode.getUse()){
            GiftCodeLoader.getInstance().removeGiftCode(giftcode);
            return;
        }
        SQLiteProvider.addPlayer(player, giftcode);
    }
}
