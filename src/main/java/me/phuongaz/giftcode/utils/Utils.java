package me.phuongaz.giftcode.utils;

import cn.nukkit.scheduler.NukkitRunnable;
import me.phuongaz.giftcode.GiftCode;
import me.phuongaz.giftcode.GiftCodeLoader;
import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    
    public static String genGiftCode(){
        String code = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        return code;
    }

    public static void runTempGift(GiftCode giftcode, int min){
        new NukkitRunnable(){
            private int time = min;

            @Override
            public void run(){
                if(this.time <= 0){
                    GiftCodeLoader.getInstance().removeGiftCode(giftcode);
                    cancel();
                }
                this.time -= 1;
            }
        }.runTaskTimer(GiftCodeLoader.getInstance(), 0, 20 * 60);
    }

}
