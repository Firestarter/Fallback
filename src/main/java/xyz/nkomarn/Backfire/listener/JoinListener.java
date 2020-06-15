package xyz.nkomarn.Backfire.listener;

import io.papermc.lib.PaperLib;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import xyz.nkomarn.Backfire.Backfire;
import xyz.nkomarn.Backfire.task.Checker;
import xyz.nkomarn.Backfire.util.Config;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");
        player.setGameMode(GameMode.ADVENTURE);
        player.setFallDistance(0);

        PaperLib.teleportAsync(player, Backfire.SPAWN).thenAccept((a) -> {
            if (!isCompatible(player)) {
                Bukkit.getLogger().warning(String.format("%s joined with an outdated version (%s).",
                        player.getName(), ProtocolSupportAPI.getProtocolVersion(player).getName()));

                String serverVersion = Config.getString("server.version");
                player.sendTitle(ChatColor.translateAlternateColorCodes('&',
                        Config.getString("messages.title.incompatible.top")),
                        ChatColor.translateAlternateColorCodes('&', String.format(
                                Config.getString("messages.title.incompatible.bottom"), serverVersion)),
                        10, 280, 20);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(
                        Config.getString("messages.chat.incompatible"), serverVersion)));
                player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
            } else {
                player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1.0f, 1.0f);

                if (!player.isOp()) {
                    if (event.getPlayer().hasPermission("backfire.priority")) {
                        Backfire.QUEUE.addFirst(player);
                    } else {
                        Backfire.QUEUE.addLast(player);
                    }
                }

                Bukkit.getScheduler().runTaskAsynchronously(Backfire.BACKFIRE, () -> {
                    if (!Backfire.STATUS.isServerUp()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Config.getString("messages.chat.down")));
                    } else if (Checker.isServerFull()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Config.getString("messages.chat.full")));
                    }
                });
            }

            player.playSound(Backfire.SPAWN, Sound.MUSIC_DISC_MALL, 5.0f, 0.7f);
        });
    }

    /**
     * Checks if the player's version is compatible with the main server.
     *
     * @param player The player object to check for version compatibility.
     * @return Whether the player's version is compatible with the main server.
     */
    private boolean isCompatible(Player player) {
        return !(ProtocolSupportAPI.getProtocolVersion(player).isBefore(
                ProtocolVersion.MINECRAFT_1_15_2));
    }
}
