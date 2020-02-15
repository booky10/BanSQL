package tk.t11e.bansql.main;
// Created by booky10 in BanSQL (14:14 15.02.20)

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tk.t11e.bansql.util.Configuration;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {

    public static final String PREFIX = "§7[§bT11E§7]§c ", NO_PERMISSION = PREFIX + "You don't have " +
            "the permissions for this!";
    public static Main main;
    private Configuration configuration;

    @Override
    public void onEnable() {
        long milliseconds = System.currentTimeMillis();

        main=this;
        configuration=new Configuration(this);
        initCommands();
        initListener(Bukkit.getPluginManager());

        milliseconds = System.currentTimeMillis() - milliseconds;
        getLogger().info("[GunGame] It took " + milliseconds + "ms to initialize this plugin!");
    }

    private void initListener(PluginManager pluginManager) {
    }

    private void initCommands() {
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}