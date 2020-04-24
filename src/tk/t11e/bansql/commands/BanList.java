package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (17:48 15.02.20)

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BanList extends CommandExecutor {


    public BanList() {
        super(Main.main, "banlist", "/banlist", "ban.banlist", Receiver.ALL,
                "blist");
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length==0) {
            sender.sendMessage("--------[Banned]--------");
            for (String name: BanTools.getBannedPlayers())
                sender.sendMessage("  - "+name);
        }else
            help(sender);
    }

    @Override
    public void onPlayerExecute(Player player, String[] args) {
        if(args.length==0) {
            player.sendMessage("§e--------§6[Banned]§e--------");
            for (String name: BanTools.getBannedPlayers())
                player.sendMessage("§e  - "+name);
        }else
            help(player);
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args,List<String> completions) {
        return completions;
    }
}