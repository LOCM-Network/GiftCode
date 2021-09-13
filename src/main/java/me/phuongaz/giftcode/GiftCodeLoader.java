package me.phuongaz.giftcode;

import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;

import cn.nukkit.plugin.PluginBase;
import me.phuongaz.giftcode.command.AdminCommand;
import me.phuongaz.giftcode.command.GiftCodeCommand;
import me.phuongaz.giftcode.provider.SQLiteProvider;

public class GiftCodeLoader extends PluginBase{

    private Map<String, GiftCode> giftcodes = new HashMap<>();
    private static GiftCodeLoader _instance;

    @Override
    public void onEnable(){
        _instance = this;
        saveDefaultConfig();
        loadCodes();
        getServer().getCommandMap().register("GiftCode", new AdminCommand());
        getServer().getCommandMap().register("GiftCode", new GiftCodeCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        try{
            SQLiteProvider.create();
        }catch(SQLException excp){
            System.out.print(excp.getMessage());
        }
    }

    public static GiftCodeLoader getInstance(){
        return _instance;
    }

    public void saveGiftCode(GiftCode giftcode){
        getConfig().set(giftcode.getGiftcode() + ".commands", giftcode.getRewards());
        if(giftcode.getPermission() != null){
            getConfig().set(giftcode.getGiftcode() + ".permission", giftcode.getPermission());
        }
        getConfig().set(giftcode.getGiftcode() + ".1use", giftcode.getUse());
        getConfig().save();
        getConfig().reload();
        this.loadCodes();
    }

    public Map<String, GiftCode> getGiftCodes(){
        return this.giftcodes;
    }

    public void loadCodes(){
        getConfig().getAll().forEach((code, cpn) -> {
            GiftCode giftcode = new GiftCode(code);
            giftcode.setRewards(getConfig().getStringList(code + ".commands"));
            giftcode.setPermission(getConfig().getString(code + ".permission"));
            giftcode.setUse(getConfig().getBoolean(code + ".1use"));
            this.giftcodes.put(code, giftcode);
        });
    }

    public void removeGiftCode(GiftCode code){
        getConfig().remove(code.getGiftcode());
        getConfig().save();
        getConfig().reload();
        this.loadCodes();
    }

    public GiftCode getGiftCodeByString(String code){
        if(this.isGiftCode(code)){
            return giftcodes.get(code);
        }
        return null;
    }

    public boolean isGiftCode(String code){
        return giftcodes.containsKey(code);
    }

}