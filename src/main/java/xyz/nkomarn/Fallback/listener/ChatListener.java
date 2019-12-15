package xyz.nkomarn.Fallback.listener;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.nkomarn.Fallback.Fallback;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final LuckPerms api = Fallback.getLuckPermsApi();
        final User user = api.getUserManager().getUser(player.getUniqueId());
        final QueryOptions queryOptions = api.getContextManager().getQueryOptions(player);
        event.setFormat(ChatColor.translateAlternateColorCodes('&', String.format(
                "%s&7%s&7: &f%s", user.getCachedData().getMetaData(queryOptions).getPrefix(),
                player.getName(), event.getMessage()
        )));
    }
}
