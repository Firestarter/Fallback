package xyz.nkomarn.Fallback.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.nkomarn.Fallback.Fallback;
import xyz.nkomarn.Fallback.util.Config;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final World endWorld = Bukkit.getWorld("world_the_end");

        event.setJoinMessage("");
        player.setPlayerListHeaderFooter(ChatColor.translateAlternateColorCodes('&',
                Config.getString("messages.tablist.header")), ChatColor.translateAlternateColorCodes('&',
                Config.getString("messages.tablist.footer")));
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(new Location(endWorld, 8.5, 15, 8.5, 180, 0));
        player.setPlayerListHeader(ChatColor.translateAlternateColorCodes('&',
                ""));

        final Location location = player.getLocation();

        // Check for incompatible client version
        if (!Fallback.isCompatible(player)) {
            final String serverVersion = Config.getString("server.version");
            player.playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.getString("messages.title.incompatible.top")),
                    ChatColor.translateAlternateColorCodes('&', String.format(Config.getString("messages.title.incompatible.bottom"),
                            serverVersion)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(Config.getString("messages.chat.incompatible"),
                    serverVersion)));
        } else {
            player.playSound(location, Sound.ITEM_TRIDENT_RETURN, 1.0f, 1.0f);
            player.playSound(location, Sound.MUSIC_NETHER, 1.0f, 1.0f);
        }

        player.playSound(new Location(endWorld, 8.5, 0.0, 8.5),
                Sound.MUSIC_DISC_STAL, 1.0f, 1.0f);
        player.playSound(location, Sound.MUSIC_NETHER, 1.0f, 1.0f);
    }
}
