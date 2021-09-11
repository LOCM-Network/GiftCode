package me.phuongaz.giftcode;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GiftCode {
    
    @Setter
    @Getter
    private String giftcode;

    @Setter
    @Getter
    private List<String> rewards;

    public GiftCode(String giftcode){
        this.giftcode = giftcode;
    }

}
