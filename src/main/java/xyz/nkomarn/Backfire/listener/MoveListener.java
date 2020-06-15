package xyz.nkomarn.Backfire.listener;

import io.papermc.lib.PaperLib;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.nkomarn.Backfire.Backfire;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getY() < 0) {
            Player player = event.getPlayer();
            PaperLib.teleportAsync(player, Backfire.SPAWN).thenAccept((a) ->
                    player.playSound(Backfire.SPAWN, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f));
        }
    }
}
