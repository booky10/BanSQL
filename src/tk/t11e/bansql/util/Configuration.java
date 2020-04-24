package tk.t11e.bansql.util;
// Created by booky10 in BanSQL (14:23 15.02.20)

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"UnusedReturnValue"})
public class Configuration extends tk.t11e.api.util.Configuration {

    private String host, database, username, password;
    private Integer port;

    public Configuration(JavaPlugin plugin) {
        super(plugin);
    }

    public void values() {
        host = getConfig().getString("data.host");
        port = getConfig().getInt("data.port");
        database = getConfig().getString("data.database");
        username = getConfig().getString("data.username");
        password = getConfig().getString("data.password");
    }

    public Integer getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}