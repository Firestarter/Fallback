package xyz.nkomarn.Fallback;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import xyz.nkomarn.Fallback.listener.*;
import xyz.nkomarn.Fallback.task.Actionbar;
import xyz.nkomarn.Fallback.task.Pinger;
import xyz.nkomarn.Fallback.util.Config;

public class Fallback extends JavaPlugin {
    private static Fallback instance;
    private static boolean online = true;
    private static long downtime = 0;
    private static LuckPerms luckPermsApi;
    private static final ViaAPI viaVersionApi = Via.getAPI();

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        RegisteredServiceProvider<LuckPerms> luckPermsProvider = Bukkit.getServicesManager()
                .getRegistration(LuckPerms.class);
        luckPermsApi = luckPermsProvider.getProvider();

        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new MoveListener(), this);
        pluginManager.registerEvents(new CommandListener(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        final BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Pinger(), 0L, 600);
        scheduler.scheduleSyncRepeatingTask(this, new Actionbar(), 0L, 20);
    }

    public void onDisable() { }

    public static Fallback getInstance() {
        return instance;
    }

    public static LuckPerms getLuckPermsApi() {
        return luckPermsApi;
    }

    public static boolean getOnline() {
        return online;
    }

    public static void setOnline(final boolean state) {
        online = state;
    }

    public static boolean isCompatible(final Player player) {
        return !(viaVersionApi.getPlayerVersion(player.getUniqueId())
                < Config.getInteger("server.protocol"));
    }

    public static long getDowntime() {
        return downtime;
    }

    public static void setDowntime(final long time) {
        downtime = time;
    }
}
