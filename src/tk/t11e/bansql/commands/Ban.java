package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (15:16 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ban extends CommandExecutor {

    public Ban() {
        super(Main.main, "permban", "/permban <player> <reason>", "ban.pban",
                Receiver.ALL, "pban");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length>=2) {
            Player target= Bukkit.getPlayer(args[0]);
            if(target!=null) {
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    reason.append(args[i]).append(" ");

                reason=new StringBuilder(ChatColor.translateAlternateColorCodes('&',reason.toString()));
                if(BanTools.banPlayerPermanent(target.getUniqueId(), reason.toString())) {
                    target.kickPlayer(BanTools.getBanMessage(reason.toString()));
                    sender.sendMessage("Successfully permanently banned " + args[0] + "!");
                }else
                    sender.sendMessage("An error happened!");
            }else
                sender.sendMessage("Unknown player!");
        }else
            help(sender);
    }

    @Override
    public void onPlayerExecute(Player player, String[] args) {
        if(args.length>=2) {
            Player target= Bukkit.getPlayer(args[0]);
            if(target!=null) {
                StringBuilder reason = new StringBuilder();
                for (int i = 1; i < args.length; i++)
                    reason.append(args[i]).append(" ");

                if(BanTools.banPlayerPermanent(target.getUniqueId(), reason.toString())) {
                    target.kickPlayer(BanTools.getBanMessage(reason.toString()));
                    player.sendMessage(Main.PREFIX + "§aSuccessfully permanently banned " + args[0] + "§a!");
                }else
                    player.sendMessage(Main.PREFIX+"An error happened!");
            }else
                player.sendMessage(Main.PREFIX+"Unknown player!");
        }else
            help(player);
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args,List<String> completions) {
        if(args.length==1)
            completions.addAll(getOnlinePlayerNames());
        return completions;
    }
}