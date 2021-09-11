package me.phuongaz.giftcode.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    
    public static String genGiftCode(){
        String code = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        return code;
    }

}
