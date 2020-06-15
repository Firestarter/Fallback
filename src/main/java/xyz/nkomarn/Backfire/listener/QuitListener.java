package xyz.nkomarn.Backfire.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.nkomarn.Backfire.Backfire;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Backfire.QUEUE.remove(event.getPlayer());
    }
}
