package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (15:16 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.api.util.UUIDFetcher;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Unban extends CommandExecutor {

    public Unban() {
        super(Main.main, "unban", "/unban <player>", "ban.unban",
                Receiver.ALL, "uban");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args, Integer length) {
        if(length==1){
            UUID target = UUIDFetcher.getUUID(args[0]);
            if(target!=null)
                if(BanTools.isBanned(target)) {
                    BanTools.unban(target);
                    sender.sendMessage("Successfully unbanned "+args[0]+"!");
                }else
                    sender.sendMessage("That player is not banned!");
            else
                sender.sendMessage("Unknown player!");
        }else
            help(sender);
    }

    @Override
    public void onExecute(Player player, String[] args, Integer length) {
        if(length==1){
            UUID target = UUIDFetcher.getUUID(args[0]);
            if(target!=null)
                if(BanTools.isBanned(target)) {
                    BanTools.unban(target);
                    player.sendMessage(Main.PREFIX+"§aSuccessfully unbanned "+args[0]+"§a!");
                }else
                    player.sendMessage(Main.PREFIX+"That player is not banned!");
            else
                player.sendMessage(Main.PREFIX+"Unknown player!");
        }else
            help(player);
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args, Integer length) {
        List<String> list = new ArrayList<>();
        if(length==1)
            list.addAll(getOnlinePlayerNames());
        return list;
    }
}