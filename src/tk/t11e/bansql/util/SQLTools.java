package tk.t11e.bansql.util;
// Created by booky10 in BanSQL (14:37 15.02.20)

import org.bukkit.Bukkit;
import tk.t11e.bansql.main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLTools {

    private String host,database,username,password;
    private Integer port;
    private Connection connection;

    public SQLTools(String host, Integer port, String database, String username, String password) {
        this.host=host;
        this.port=port;
        this.database=database;
        this.username=username;
        this.password=password;

        initConnection();
    }

    public void initConnection() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed())
                    return;

                this.connection =DriverManager.getConnection("jdbc:mysql://" + this.host + ":" +
                                this.port + "/" + this.database,this.username, this.password);
                Bukkit.getConsoleSender().sendMessage("[BanSQL] §eSuccessfully connected to database!");
            }
        } catch (SQLException exception) {
            Bukkit.broadcastMessage("§e"+exception.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }

    public Integer getPort() {
        return port;
    }

    public Connection getConnection() {
        return connection;
    }
}