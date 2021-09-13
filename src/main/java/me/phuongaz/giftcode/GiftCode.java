package me.phuongaz.giftcode;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GiftCode {

    private String giftcode;
    private String permission;
    private List<String> rewards;
    private Boolean use;

    public GiftCode(String giftcode){
        this.giftcode = giftcode;
    }

}
