package xyz.nkomarn.Fallback.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }
}
