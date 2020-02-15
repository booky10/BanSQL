package tk.t11e.bansql.util;
// Created by booky10 in BanSQL (14:23 15.02.20)

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"UnusedReturnValue", "ResultOfMethodCallIgnored"})
public class Configuration {

    private File file;
    private FileConfiguration config;
    private String host, database, username, password;
    private Integer port;

    public Configuration(File file) {
        this.file = file;

        init();
    }

    public Configuration(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/config.yml");

        init();
    }

    public Configuration init() {
        if (!this.file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
        values();

        return this;
    }

    public Configuration init(File file) {
        this.file = file;
        init();

        return this;
    }

    public Configuration values() {
        this.host = this.config.getString("data.host");
        this.port = this.config.getInt("data.port");
        this.database = this.config.getString("data.database");
        this.username = this.config.getString("data.username");
        this.password = this.config.getString("data.password");

        return this;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
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

    public Configuration setFile(File file) {
        this.file = file;
        init();

        return this;
    }
}