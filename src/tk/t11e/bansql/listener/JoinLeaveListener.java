package tk.t11e.bansql.listener;
// Created by booky10 in BanSQL (14:55 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import tk.t11e.bansql.main.Main;
import tk.t11e.bansql.util.BanTools;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class JoinLeaveListener implements Listener {

    Main main = Main.main;

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (BanTools.isBanned(player.getUniqueId())) {
            String unban = BanTools.getUnbanTime(player.getUniqueId());
            if (Objects.requireNonNull(unban).equals("permanent")) {
                event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                event.setKickMessage(BanTools.getBanMessage(BanTools.getReason(player.getUniqueId())));
            } else {
                long unbanTime = Long.parseLong(unban);

                if (unbanTime < System.currentTimeMillis()) {
                    BanTools.unban(player.getUniqueId());
                } else {
                    event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                    event.setKickMessage(BanTools.getTempBanMessage(BanTools
                            .getReason(player.getUniqueId()), unbanTime));
                }
            }
        }
    }
}