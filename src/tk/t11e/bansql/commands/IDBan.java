package tk.t11e.bansql.commands;
// Created by booky10 in BanSQL (11:34 24.04.20)

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.t11e.api.commands.CommandExecutor;
import tk.t11e.bansql.main.Main;

import java.util.List;
import java.util.Objects;

public class IDBan extends CommandExecutor {


    public IDBan() {
        super(Main.main, "idban", "/idban <player> <ID> [Reason]", "ban.id", Receiver.ALL);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        StringBuilder reason = new StringBuilder("-");
        if (args.length > 2) {
            reason = new StringBuilder();
            for (int i = 2; i < args.length; i++)
                reason.append(args[i]).append(" ");
        } else {
            help(sender);
            return;
        }
        try {
            IDBanList.IDs ID = IDBanList.IDs.getByID(Integer.parseInt(args[1]));
            Bukkit.dispatchCommand(sender, Objects.requireNonNull(ID).getConsoleBanCommand(args[0], reason.toString()));
        } catch (NullPointerException | NumberFormatException exception) {
            sender.sendMessage("Das ist keine richtige ID!");
            sender.sendMessage("Um alle IDs zu sehen, gib \"/idbanlist\" ein!");
        }
    }

    @Override
    public void onPlayerExecute(Player player, String[] args) {
        StringBuilder reason = new StringBuilder("-");
        if (args.length > 2) {
            reason = new StringBuilder();
            for (int i = 2; i < args.length; i++)
                reason.append(args[i]).append(" ");
        } else {
            help(player);
            return;
        }
        try {
            if (!player.hasPermission("ban.id." + Integer.parseInt(args[1]))) {
                player.sendMessage(Main.PREFIX + "Dazu hast du keine Rechte!");
                return;
            }
            IDBanList.IDs ID = IDBanList.IDs.getByID(Integer.parseInt(args[1]));
            player.chat(Objects.requireNonNull(ID).getBanCommand(args[0], reason.toString()));
        } catch (NullPointerException | NumberFormatException exception) {
            player.sendMessage(Main.PREFIX + "Das ist keine richtige ID!");
            player.sendMessage(Main.PREFIX + "Um alle IDs zu sehen, gib \"/idbanlist\" ein!");
        }
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args, List<String> completions) {
        if (args.length == 1)
            completions.addAll(getOnlinePlayerNames());
        else if (args.length == 2)
            for (int i = 1; i <= IDBanList.IDs.getMaxID(); i++)
                completions.add(Integer.toString(i));
        return null;
    }
}