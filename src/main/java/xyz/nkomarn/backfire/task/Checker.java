package xyz.nkomarn.backfire.task;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.nkomarn.backfire.Backfire;
import xyz.nkomarn.backfire.util.MineStat;

import java.util.concurrent.TimeUnit;

public class Checker implements Runnable {

    private final MineStat status;
    private int downtime;

    public Checker(@NotNull Backfire backfire) {
        MineStat status = new MineStat(backfire.getConfig().getString("server.hostname"), backfire.getConfig().getInt("server.port"));
        status.setTimeout(2);
        this.status = status;
    }

    @Override
    public void run() {
        status.refresh();

        if (status.isServerUp()) {
            downtime = 0;
            return;
        }

        downtime += 5;

        int minutes = (int) TimeUnit.SECONDS.toMinutes(downtime);
        TextComponent notification = new TextComponent("✗ Main server currently down (downtime: " + minutes + " minutes).");
        notification.setColor(ChatColor.of("#ff424c"));
        notification.setBold(true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, notification);
        }
    }

    /*private void sendPlayerToMain(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(Config.getString("server.name"));
        player.sendPluginMessage(Backfire.BACKFIRE, "BungeeCord", out.toByteArray());
    }*/
}
