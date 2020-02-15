package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (15:16 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TempBan extends CommandExecutor {

    public TempBan() {
        super(Main.main, "tempban", "/tempban <player> <time> <unit> <reason>",
                "ban.tban", Receiver.ALL, "tban");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args, Integer length) {
        if(args.length>=4) {
            Player target= Bukkit.getPlayer(args[0]);
            if(target!=null) {
                long time;
                TimeUnit unit;

                try {
                    time = Long.parseLong(args[1]);
                } catch (NumberFormatException exception) {
                    sender.sendMessage("Unknown number!");
                    return;
                }
                try {
                    unit = TimeUnit.valueOf(args[2].toUpperCase());
                } catch (IllegalArgumentException exception) {
                    sender.sendMessage("Unknown unit!");
                    return;
                }

                long unban = TimeUnit.MILLISECONDS.convert(time, unit);

                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    reason.append(args[i]).append(" ");

                if(BanTools.banPlayerTemp(target.getUniqueId(), reason.toString(),unban)) {
                    target.kickPlayer(BanTools.getTempBanMessage(reason.toString(),unban));
                    sender.sendMessage("Successfully temporarily banned " + args[0] +
                            "!");
                }else
                    sender.sendMessage("An error happened!");
            }else
                sender.sendMessage("Unknown player!");
        }else
            help(sender);
    }

    @Override
    public void onExecute(Player player, String[] args, Integer length) {
        if(length>=4) {
            Player target= Bukkit.getPlayer(args[0]);
            if(target!=null) {
                long time;
                TimeUnit unit;

                try {
                    time = Long.parseLong(args[1]);
                } catch (NumberFormatException exception) {
                    player.sendMessage(Main.PREFIX+"Unknown number!");
                    return;
                }
                try {
                    unit = TimeUnit.valueOf(args[2].toUpperCase());
                } catch (IllegalArgumentException exception) {
                    player.sendMessage(Main.PREFIX+"Unknown unit!");
                    return;
                }

                long unban = TimeUnit.MILLISECONDS.convert(time, unit);

                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    reason.append(args[i]).append(" ");

                if(BanTools.banPlayerTemp(target.getUniqueId(), reason.toString(),unban)) {
                    target.kickPlayer(BanTools.getTempBanMessage(reason.toString(),unban));
                    player.sendMessage(Main.PREFIX + "§aSuccessfully temporarily banned " + args[0] +
                            "§a!");
                }else
                    player.sendMessage(Main.PREFIX+"An error happened!");
            }else
                player.sendMessage(Main.PREFIX+"Unknown player!");
        }else
            help(player);
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args, Integer length) {
        List<String> list = new ArrayList<>();
        if(length==1)
            list.addAll(getOnlinePlayerNames());
        else if(length==3)
            for (TimeUnit unit:TimeUnit.values())
                list.add(unit.toString());
        return list;
    }
}