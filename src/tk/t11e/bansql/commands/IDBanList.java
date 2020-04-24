package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (11:34 24.04.20)

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.bansql.main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IDBanList extends CommandExecutor {


    public IDBanList() {
        super(Main.main, "idbanlist", "/idbanlist", "ban.id.list", Receiver.ALL);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length==0){
            for (String message:IDs.getStringList())
                sender.sendMessage(ChatColor.stripColor(message));
        }else
            help(sender);
    }

    @Override
    public void onPlayerExecute(Player player, String[] args) {
        if(args.length==0){
            for (String message:IDs.getStringList())
                player.sendMessage(message);
        }else
            help(player);
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args, List<String> completions) {
        return completions;
    }

    public enum IDs {
        HACK_ANTI_KNOCK_BACK(1, 16L, TimeUnit.DAYS, "AntiKnockback", "Hacks"),
        HACK_KILL_AURA(2, 20L, TimeUnit.DAYS, "KillAura", "Hacks"),
        HACK_FLY(3, 3L * 7L, TimeUnit.DAYS, "Fly", "Hacks"),
        HACK_SPEED(4, 3L * 7L, TimeUnit.DAYS, "Speed", "Hacks"),
        HACK_OTHER(5, 20L, TimeUnit.DAYS, "Sonstiges", "Hacks"),

        OFFENCE_TEAM(6, 3L, TimeUnit.DAYS, "Team", "Beleidigungen"),
        OFFENCE_PLAYER(7, 3L, TimeUnit.DAYS, "Spieler", "Beileidigungen"),
        OFFENCE_NETWORK(8, 7L, TimeUnit.DAYS, "Netzwerk", "Beileidigungen"),

        OTHER_BUG_USING(9, 12L, TimeUnit.HOURS, "Bugusing", "Sonstiges"),
        OTHER_THREAT(10, 3L, TimeUnit.DAYS, "Drohung", "Sonstiges"),
        OTHER_RADICALISM(11, 3L * 7L, TimeUnit.DAYS, "Radikalismus", "Sonstiges"),
        OTHER_ADVERTISING(12, 7L, TimeUnit.DAYS, "Werbung", "Sonstiges"),
        OTHER_PVP_LOG(13, 3L, TimeUnit.HOURS, "PvP Log", "Sonstiges"),

        FURTHER_LIGHT_OFFENCE(14, 3L, TimeUnit.HOURS, "Leichte Beleidigung", "Weiteres"),
        FURTHER_NO_RESPECT(15, 6L, TimeUnit.HOURS, "Respektloses Verhalten", "Weiteres"),
        FURTHER_REPORT_ABUSE(16, 2L, TimeUnit.DAYS, "Report Abuse", "Weiteres"),
        FURTHER_PROVOCATION_OFFENCE(17, 6L, TimeUnit.HOURS, "Provokation von Beleidigungen", "Weiteres"),
        FURTHER_PROVOCATION(18, 6L, TimeUnit.HOURS, "Allgemeine Provokation", "Weiteres"),
        FURTHER_BAN_EVADING(19, Long.MAX_VALUE, null, "Ban Umgehung", "Weiteres"),
        FURTHER_SCAMMING(20, 7L, TimeUnit.DAYS, "Scamming", "Weiteres"),

        EXTREME_EXTREME(21, Long.MAX_VALUE, null, "Extrem", "Extrem");

        private final Integer ID;
        private final Boolean permanent;
        private final Long amount;
        private final TimeUnit timeUnit;
        private final String name, category;

        IDs(Integer ID, Long amount, TimeUnit timeUnit, String name, String category) {
            this.ID = ID;

            this.amount = amount;
            this.timeUnit = timeUnit;
            this.permanent = Long.MAX_VALUE == amount;

            this.name = ChatColor.stripColor(name);
            this.category = ChatColor.stripColor(category);
        }

        public Boolean isPermanent() {
            return permanent;
        }

        public Long getTimeAmount() {
            return amount;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public Integer getID() {
            return ID;
        }

        @NotNull
        public static IDs getByID(Integer ID) {
            for (IDs IDEnum : values())
                if (IDEnum.ID.equals(ID))
                    return IDEnum;
            return null;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public static List<String> getStringList() {
            List<String> stringList = new ArrayList<>();
            for (IDs ID : values())
                stringList.add("  §7» §8" + ID.category + " §7|§c " + ID.name + "§b: " + ID.ID);
            return stringList;
        }

        public String getBanReason() {
            return getBanReason("-");
        }

        public String getBanReason(String reason) {
            return "§7»§8 " + category + " §7|§c " + name + "§b: " + ID + "§7 (" + ChatColor.stripColor(reason) + "§7)";
        }

        public String getBanCommand(String name) {
            return getBanCommand(name, "-");
        }

        public String getBanCommand(String name, String reason) {
            String realReason = getBanReason(reason).replace('§', '&');
            if (this.permanent) {
                String command = "/bansql:pban %s %s";
                return String.format(command, name, realReason);
            } else {
                String command = "/bansql:tban %s %s %s %s";
                String amount = this.amount.toString();
                String timeUnit = this.timeUnit.toString().toUpperCase();
                return String.format(command, name, amount, timeUnit, realReason);
            }
        }

        public static Integer getMaxID() {
            Integer maxID = Integer.MIN_VALUE;
            for (IDs ID : values())
                if (ID.ID > maxID)
                    maxID = ID.ID;
            return maxID;
        }

        public String getConsoleBanCommand(String name) {
            return getConsoleBanCommand(name, "-");
        }

        public String getConsoleBanCommand(String name, String reason) {
            return getBanCommand(name, reason).replaceFirst("/", "");
        }
    }
}