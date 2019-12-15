package xyz.nkomarn.Fallback.task;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import xyz.nkomarn.Fallback.Fallback;
import xyz.nkomarn.Fallback.util.Config;

public class Actionbar implements Runnable {
    @Override
    public void run() {
        final boolean online = Fallback.getOnline();
        String message;

        if (online) {
            message = Config.getString("messages.actionbar.online");
            Fallback.setDowntime(0); // Reset downtime
        } else {
            final long downtime = Fallback.getDowntime() + 1000;
            message = String.format(Config.getString("messages.actionbar.down"), (downtime /1000) / 60);
            Fallback.setDowntime(downtime);
        }

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (!Fallback.isCompatible(player)) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', String.format(
                                Config.getString("messages.actionbar.incompatible"), Config.getString("server.version")
                        ))));
                return;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        });
    }
}
