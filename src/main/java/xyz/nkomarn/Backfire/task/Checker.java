package xyz.nkomarn.Backfire.task;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.nkomarn.Backfire.Backfire;
import xyz.nkomarn.Backfire.util.Config;

import java.util.concurrent.TimeUnit;

public class Checker implements Runnable {
    @Override
    public void run() {
        boolean currentStatus = Backfire.STATUS.isServerUp();
        Backfire.STATUS.refresh();

        if (!Backfire.STATUS.isServerUp()) {
            Backfire.DOWNTIME += 5;
            Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', String.format(
                            Config.getString("messages.actionbar.down"), TimeUnit.SECONDS.toMinutes(Backfire.DOWNTIME)
                    )))));
        } else if (!currentStatus && Backfire.STATUS.isServerUp()) {
            Bukkit.getServer().getOnlinePlayers().forEach(player -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getString("messages.chat.up")));
                if (!player.isOp()) sendPlayerToMain(player);
            });
            Backfire.DOWNTIME = 0;
        } else if (isServerFull()) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor
                        .translateAlternateColorCodes('&', String.format(Config.getString("messages.actionbar.queue"),
                                getPositionInQueue(player) + 1, (Backfire.QUEUE.size())
                        ))));
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 0.2f);
            });
        } else {
            if (!Backfire.QUEUE.isEmpty() && !isServerFull()) {
                Player queuedPlayer = Backfire.QUEUE.getFirst();
                queuedPlayer.playSound(queuedPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.0f);
                sendPlayerToMain(queuedPlayer);
            }
        }
    }

    public static boolean isServerFull() {
        return Integer.parseInt(Backfire.STATUS.getCurrentPlayers()) >=
                Integer.parseInt(Backfire.STATUS.getMaximumPlayers());
    }

    private int getPositionInQueue(Player player) {
        int counter = 0;
        for (Player queuedPlayer : Backfire.QUEUE) {
            if (queuedPlayer.equals(player)) {
                return counter;
            } else counter++;
        }
        return counter;
    }

    private void sendPlayerToMain(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(Config.getString("server.name"));
        player.sendPluginMessage(Backfire.BACKFIRE, "BungeeCord", out.toByteArray());
    }
}
