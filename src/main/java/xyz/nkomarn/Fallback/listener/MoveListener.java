package xyz.nkomarn.Fallback.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getY() < 0) {
            final Player player = event.getPlayer();
            final Location location = new Location(Bukkit.getWorld("world_the_end"), 8.5, 15, 8.5, 180, 0);
            player.teleport(location);
            player.playSound(location, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }
}
