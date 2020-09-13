package xyz.nkomarn.backfire;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import xyz.nkomarn.backfire.listener.*;
import xyz.nkomarn.backfire.task.Checker;

import java.util.Arrays;

public class Backfire extends JavaPlugin {

    private final ViaAPI<Player> viaAPI = Via.getAPI();

    public void onEnable() {
        saveDefaultConfig();

        Arrays.asList(
                new CommandListener(),
                new JoinListener(this),
                new QuitListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getScheduler().runTaskTimerAsynchronously(this, new Checker(this), 0L, 100);
    }

    @NotNull
    public ViaAPI<Player> getViaAPI() {
        return this.viaAPI;
    }
}
