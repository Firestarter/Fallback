package xyz.nkomarn.Fallback.task;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import xyz.nkomarn.Fallback.Fallback;
import xyz.nkomarn.Fallback.util.Config;
import xyz.nkomarn.Fallback.util.MineStat;

public class Pinger implements Runnable {
    @Override
    public void run() {
        final MineStat status = new MineStat(Config.getString("server.hostname"),
                Config.getInteger("server.port"));
        final boolean online = status.isServerUp();
        Fallback.setOnline(online);
        if (online) {
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("survival");
                player.sendPluginMessage(Fallback.getInstance(), "BungeeCord", out.toByteArray());
            });
        }
    }
}
