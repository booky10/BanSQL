package tk.t11e.bansql.util;
// Created by booky10 in BanSQL (15:21 15.02.20)

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import tk.t11e.api.util.UUIDFetcher;
import tk.t11e.bansql.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BanTools {

    private static Main main = Main.main;

    public static Boolean isBanned(UUID uuid) {
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("SELECT UUID FROM ban_sql WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();

            if (result.next())
                return true;

        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
        return false;
    }

    @NotNull
    public static String getReason(UUID uuid) {
        if (!isBanned(uuid)) return null;
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("SELECT Reason FROM ban_sql WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();

            if(result.next())
                return result.getString(1);

        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
        return null;
    }

    public static Boolean banPlayerPermanent(UUID uuid, String reason) {
        if (isBanned(uuid)) return false;
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("INSERT INTO ban_sql (UUID,Reason,unban) VALUES (?,?,?)");

            statement.setString(1, uuid.toString());
            statement.setString(2, reason);
            statement.setString(3, "permanent");

            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
            return false;
        }
    }

    public static Boolean banPlayerTemp(UUID uuid, String reason,Long unban) {
        if (isBanned(uuid)) return false;
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("INSERT INTO ban_sql (UUID,Reason,unban) VALUES (?,?,?)");

            statement.setString(1, uuid.toString());
            statement.setString(2, reason);
            statement.setString(3, unban.toString());

            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
            return false;
        }
    }

    public static String getUnbanTime(UUID uuid) {
        if (!isBanned(uuid)) return null;
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("SELECT unban FROM ban_sql WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();

            if(result.next())
                return result.getString(1);

        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
        return null;
    }

    public static void unban(UUID uuid) {
        if(!isBanned(uuid)) return;

        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("DELETE FROM ban_sql WHERE UUID=?");
            statement.setString(1, uuid.toString());

            statement.executeUpdate();
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
    }

    public static List<String> getBannedPlayers() {
        try {
            PreparedStatement statement = main.getSqlTools().getConnection()
                    .prepareStatement("SELECT UUID FROM ban_sql");
            ResultSet result= statement.executeQuery();
            List<String> UUIDs=new ArrayList<>();
            List<String> names=new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                UUIDs.add(result.getString(1));
            }

            for (String uuid:UUIDs)
                names.add(UUIDFetcher.getName(UUID.fromString(uuid)));
            return names;
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
            return Collections.emptyList();
        }
    }

    public static String getBanMessage(String reason) {
        return "§cYou were permanently banned from this server!\n" +
                "§cIf you want to get unbanned, ask one of the admins!\n"+
                "§r\n" +
                "§7Reason:§r " + reason;
    }

    public static String getTempBanMessage(String reason, Long unban) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(unban);
        String unbanStr = time.getTime().toString();

        return "§cYou are temporarily banned from this server!\n" +
                "§cIf you want to get earlier unbanned, ask one of the admins!\n"+
                "§r\n" +
                "§7Reason:§r " + reason + "§r\n" +
                "§cYou are banned till§r " + unbanStr;
    }
}