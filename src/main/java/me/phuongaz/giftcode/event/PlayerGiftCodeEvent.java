package me.phuongaz.giftcode.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import lombok.Getter;
import me.phuongaz.giftcode.GiftCode;

public class PlayerGiftCodeEvent extends PlayerEvent{
    
    public static HandlerList handlerList = new HandlerList();

    @Getter
    private GiftCode giftcode;

    public PlayerGiftCodeEvent(Player player, GiftCode giftcode){
        this.player = player;
        this.giftcode = giftcode;
    }

    public static HandlerList getHandlers(){
		return handlerList;
	}
}
