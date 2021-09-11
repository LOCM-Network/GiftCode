package me.phuongaz.giftcode.provider;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import me.phuongaz.giftcode.GiftCode;
import me.phuongaz.giftcode.GiftCodeLoader;
import ru.nukkit.dblib.DbLib;

public class SQLiteProvider {

    public SQLiteProvider(){
        try{
            create();
        }catch(SQLException excp){
            System.out.print(excp.getMessage());
        }
        
    }
 
    public static Connection connectToSQLite() throws SQLException {
        return connectToSQLite("players.db");
    }

    public static Connection connectToSQLite(String filename) throws SQLException {
        File file = new File(GiftCodeLoader.getInstance().getDataFolder() + File.separator + filename);
        Connection connection = DbLib.getSQLiteConnection(file);
        return connection;
    }

    public static boolean executeUpdate(String query) throws SQLException {
        Connection connection = connectToSQLite();
        if (connection == null) return false;
        connection.createStatement().executeUpdate(query);
        if (connection != null) connection.close();
        return true;
    }


    public static List<String> executeSelect(String query) throws SQLException {
        List<String> list = new ArrayList<String>();
        Connection connection = connectToSQLite();
        if (connection == null) return list;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            list.add(resultSet.getString("name") + " " + resultSet.getString("giftcode"));
        }
        if (connection != null) connection.close();
        return list;
    }

    public static void addPlayer(Player player, GiftCode code){
        String query = "insert into gc (name,giftcode) values ('"+ player.getName() +"','" + code.getGiftcode() +"')";
        try{
            executeUpdate(query);
        }catch(SQLException ecp){
            System.out.println(ecp.getMessage());
        }
    }

    public static boolean exists(Player player, GiftCode code){
        Boolean yes = null;
        String query = "select * from gc where name='"+ player.getName() +"'";
        try{
            List<String> list = executeSelect(query);
            if(list.isEmpty())  yes = false;
            if(list.contains(code.getGiftcode())) yes = true;
        }catch(SQLException ecp){
            System.out.println(ecp.getMessage());
        }
        return (yes == null) ? false : true;
    }

    public static void create() throws SQLException{
        try{
            String query = "create table if not exists gc (name varchar(20), giftcode varchar(20))";
            executeUpdate(query);
        }catch(SQLException ecp){
            System.out.println(ecp);
        }
    }
    
}
