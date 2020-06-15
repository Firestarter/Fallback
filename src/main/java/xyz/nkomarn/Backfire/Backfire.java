package xyz.nkomarn.Backfire;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nkomarn.Backfire.listener.*;
import xyz.nkomarn.Backfire.task.Checker;
import xyz.nkomarn.Backfire.util.Config;
import xyz.nkomarn.Backfire.util.MineStat;

import java.util.ArrayDeque;
import java.util.Deque;

public class Backfire extends JavaPlugin {
    public static Backfire BACKFIRE;
    public static MineStat STATUS;
    public static int DOWNTIME = 0;

    public static Deque<Player> QUEUE = new ArrayDeque<>();
    public static final Location SPAWN = new Location(Bukkit.getWorld("world_the_end"), 7, 6.3, 25, 0, 0);

    public void onEnable() {
        BACKFIRE = this;
        saveDefaultConfig();

        STATUS = new MineStat(
                Config.getString("server.hostname"),
                Config.getInteger("server.port")
        );
        STATUS.setTimeout(2);
        STATUS.refresh();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new MoveListener(), this);
        pluginManager.registerEvents(new CommandListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getScheduler().runTaskTimerAsynchronously(this, new Checker(), 0L, 100);
    }

    public void onDisable() { }
}
