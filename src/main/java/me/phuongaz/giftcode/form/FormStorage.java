package me.phuongaz.giftcode.form;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.utils.TextFormat;
import me.phuongaz.giftcode.GiftCode;
import me.phuongaz.giftcode.GiftCodeLoader;
import me.phuongaz.giftcode.event.PlayerGiftCodeEvent;
import me.phuongaz.giftcode.provider.SQLiteProvider;
import me.phuongaz.giftcode.utils.Utils;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.Input;

public class FormStorage {

    public static void sendMainForm(Player player){
        SimpleForm form = new SimpleForm("Giftcode form");
        form.addButton("Create giftcode", (p, button) -> {
            sendCreateForm(player);
        });

        form.addButton("list giftcode", (p, button) -> {
            listGiftCode(player);
        });
        form.send(player);
    }

    public static void sendCreateForm(Player player){
        CustomForm form = new CustomForm("Create form");
        String code = Utils.genGiftCode();
        form.addElement("code", new Input("Edit: ", code, "LOCM" + code));
        form.setHandler((p, response) -> {
            String after_code = response.getInput("code").getValue();
            if(code.equals("")){
                sendMainForm(player);
                return;
            }
            GiftCode giftcode = new GiftCode(after_code);
            List<String> rws = new ArrayList<>();
            giftcode.setRewards(rws);
            GiftCodeLoader.getInstance().saveGiftCode(giftcode);
            p.sendMessage("Create giftcode: " + after_code);
        });
        form.send(player);
    }

    public static void listGiftCode(Player player){
        SimpleForm form = new SimpleForm("Codes");
        GiftCodeLoader.getInstance().getGiftCodes().keySet().forEach(key -> {
            GiftCode code = GiftCodeLoader.getInstance().getGiftCodes().get(key);
            form.addButton(code.getGiftcode(), (p, button) -> {
                editForm(player, code);
            });
        });
        form.setNoneHandler((p) -> sendMainForm(player));
        form.send(player);
    }
    
    public static void editForm(Player player, GiftCode giftcode){
        String code = giftcode.getGiftcode();
        CustomForm form = new CustomForm("Edit code: " + code);
        form.addElement("code", new Input("Edit code:", code, code));
        form.addElement("rewards", new Input("Commands: (# = next command, {player} = player)"));
        form.setHandler((p, response) -> {
            List<String> rws = new ArrayList<>();
            String after_code = response.getInput("code").getValue();
            for(String rw : response.getInput("rewards").getValue().split("#")){
                rws.add(rw);
            }
            giftcode.setRewards(rws);
            giftcode.setGiftcode(after_code);
            GiftCodeLoader.getInstance().getConfig().remove(code);
            GiftCodeLoader.getInstance().saveGiftCode(giftcode);
        });
        form.setNoneHandler((p) -> sendMainForm(player));
        form.send(player);
    }

    public static void playerForm(Player player){
        GiftCodeLoader loader = GiftCodeLoader.getInstance();
        CustomForm form = new CustomForm("LOCM GIFTCODE");
        form.addElement("code", new Input("Nhap giftcode:"));
        form.addElement(TextFormat.colorize("Giftcode chua su dung"));
        loader.getGiftCodes().keySet().forEach(key -> {
            GiftCode code = loader.getGiftCodes().get(key);
            if(!SQLiteProvider.exists(player, code)){
                form.addElement("- " + code.getGiftcode());
            }
        });
        form.setHandler((p, response) -> {
            String code = response.getInput("code").getValue().toUpperCase();
            if(loader.isGiftCode(code)){
                GiftCode giftcode = GiftCodeLoader.getInstance().getGiftCodeByString(code);
                if(SQLiteProvider.exists(p, giftcode){
                    p.sendMessage(TextFormst.colorize("&l&cGiftcode đã sử dụng");
                    return;
                }
                Server.getInstance().getPluginManager().callEvent(new PlayerGiftCodeEvent(player, giftcode));
            }
        });
        form.send(player);
    }
    
}
