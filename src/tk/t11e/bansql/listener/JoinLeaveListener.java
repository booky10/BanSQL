package tk.t11e.bansql.listener;
// Created by booky10 in BanSQL (14:55 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.util.Objects;

public class JoinLeaveListener implements Listener {

    Main main = Main.main;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            if (BanTools.isBanned(player.getUniqueId())) {
                String unban = BanTools.getUnbanTime(player.getUniqueId());
                if (Objects.requireNonNull(unban).equals("permanent")) {
                    /*if (!player.isOnline()) {
                        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                        event.setKickMessage(BanTools.getBanMessage(BanTools.getReason(player.getUniqueId())));
                    } else*/
                    String banMessage = BanTools.getBanMessage(BanTools.getReason(player.getUniqueId()));
                    Bukkit.getScheduler().runTaskLater(main, () -> player.kickPlayer(banMessage), 30);
                } else {
                    long unbanTime = Long.parseLong(unban);
                    if (unbanTime < System.currentTimeMillis())
                        BanTools.unban(player.getUniqueId());
                    /*else if (!player.isOnline()) {
                        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                        event.setKickMessage(BanTools.getTempBanMessage(BanTools.getReason(player.getUniqueId())
                                , unbanTime));
                    }*/
                    else {
                        String banMessage = BanTools.getTempBanMessage(BanTools.getReason(player.getUniqueId()),
                                unbanTime);
                        Bukkit.getScheduler().runTaskLater(main, () -> player.kickPlayer(banMessage), 30);
                    }
                }
            }
        });
    }
}