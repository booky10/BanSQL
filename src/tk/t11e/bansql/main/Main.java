package tk.t11e.bansql.main;
// Created by booky10 in BanSQL (14:14 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.bansql.commands.Ban;
import tk.t11e.bansql.commands.BanList;
import tk.t11e.bansql.commands.TempBan;
import tk.t11e.bansql.commands.Unban;
import tk.t11e.bansql.listener.JoinLeaveListener;
import tk.t11e.bansql.util.Configuration;
import tk.t11e.bansql.util.SQLTools;

import java.sql.SQLException;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§bT11E§7]§c ", NO_PERMISSION = PREFIX + "You don't have " +
            "the permissions for this!";
    public static Main main;
    private Configuration config;
    private SQLTools sqlTools;

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();

        main=this;
        config=new Configuration(this);
        sqlTools=new SQLTools(config.getHost(),config.getPort(),config.getDatabase(),
                config.getUsername(),config.getPassword());
        initCommands();
        initListener(Bukkit.getPluginManager());

        milliseconds = System.currentTimeMillis() - milliseconds;
        getLogger().info("[GunGame] It took " + milliseconds + "ms to initialize this plugin!");
    }

    @Override
    public void onDisable() {
        try {
            sqlTools.getConnection().close();
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
    }

    private void initListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new JoinLeaveListener(), this);
    }

    private void initCommands() {
        new Ban().init();
        new TempBan().init();
        new Unban().init();
        new BanList().init();
    }

    public Configuration getConfiguration() {
        return config;
    }

    public SQLTools getSqlTools() {
        return sqlTools;
    }
}